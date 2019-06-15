import random
import gym
import numpy as np
from tqdm import tqdm
from collections import deque
from keras.models import Sequential, model_from_config
from keras.layers import Dense, Input
from keras.optimizers import Adam
from keras import optimizers
import pandas as pd
import matplotlib.pyplot as plt

class DQN():
  def __init__(self, num_episodes=2000, gamma=0.995, alpha=0.05, 
      alpha_decay=1e-4, epsilon=1, epsilon_decay=0.88, epsilon_min = 0.001,
      win_threshold=200, batch_size=64, memory_size=1000, tau=0.05):
      self.env = gym.make('LunarLander-v2')
      self.memory = deque(maxlen=memory_size)
      self.memory_size = memory_size
      self.num_states = self.env.observation_space.shape[0]
      self.num_actions = self.env.action_space.n
      
      self.num_episodes = num_episodes
      self.gamma = gamma
      self.alpha = alpha
      self.alpha_decay = alpha_decay
      self.epsilon = epsilon
      self.epsilon_decay = epsilon_decay
      self.epsilon_min = epsilon_min
      self.win_threshold = win_threshold
      self.batch_size = batch_size
      self.tau = tau
      
      self.model = Sequential()
      self.model.add(Dense(64, input_dim=self.num_states, activation='relu'))
      self.model.add(Dense(self.num_actions))
      self.model.compile(loss='mean_squared_error', optimizer=Adam(lr=self.alpha))


  def select_action(self, state, episode, n_episodes):
      predicted_act = self.model.predict(state) # predict action from the state
      return random.randrange(self.num_actions) if (np.random.rand() < self.epsilon) \
      else np.argmax(predicted_act[0]) # select larger value action


  def replay_exp(self, batch_size, target_model, tau):
      state_batch = [] 
      target_batch = []
      sample_batch = random.sample(self.memory, batch_size)
      updates = self.update_target_weights(target_model.get_weights(), self.model.get_weights(), tau)
      target_model.set_weights(np.array(updates))
      
      for state, action, reward, state_prime, done in sample_batch:
        state_target = self.model.predict(state)
        cloned_state_target = self.target_model.predict(state)

        state_target[0][action] = reward if done else reward + self.gamma * np.amax(cloned_state_target[0])
        state_batch.append(state[0])
        target_batch.append(state_target[0])
      self.model.fit(np.array(state_batch), np.array(target_batch), batch_size=len(sample_batch), verbose=0) 

      if self.epsilon > self.epsilon_min:
        self.epsilon *= self.epsilon_decay
      return target_model


  def update_target_weights(self, target, source, tau):
      updates = []
      for tw, sw in zip(target, source):
          updates.append(tau * sw + (1. - tau) * tw)
      return updates

  def clone_model(self, model, custom_objects={}):
      config = {
          'class_name': model.__class__.__name__,
          'config': model.get_config(),
      }
      clone = model_from_config(config, custom_objects=custom_objects)
      clone.set_weights(model.get_weights())
      return clone


  def learn(self):
      episode_rewards = [] 
      trial_rewards = []
      trials = []
      self.target_model = self.clone_model(self.model)

      for e in tqdm(range(self.num_episodes)):
          state = np.reshape(self.env.reset(), (1, self.num_states))
          done = False

          total_reward = 0
          while not done:
              # self.env.render()
              action = self.select_action(state, e, self.num_episodes)
              state_prime, reward, done, _ = self.env.step(action)
              state_prime = np.reshape(state_prime, [1, self.num_states])
              self.memory.append((state, action, reward, state_prime, done))
              state = state_prime
              total_reward+=reward
              if total_reward >= 200:
                  self.epsilon = max(self.epsilon/3., self.epsilon_min)
          if len(self.memory) > self.batch_size and e < self.num_episodes-100:
            self.target_model = self.replay_exp(self.batch_size, self.target_model, self.tau)

          if e < self.num_episodes-100:
            episode_rewards.append(total_reward)
            reward_ave = np.mean(episode_rewards)

          if e >= self.num_episodes-100:
            trial_rewards.append(total_reward)
            reward_ave = np.mean(trial_rewards)

          if done:
            print("episode: {}/{}, score: {}, e: {:.2}".format(e, self.num_episodes, total_reward, float(self.epsilon)))        

          # if e >= 100 and reward_ave >= self.win_threshold:
          #   print('score {} in average'.format(reward_ave))
          #   break

      return episode_rewards, trial_rewards

  def plot_rewards(self, title, title2, xlabel, ylabel, episode_rewards, trial_rewards):
    episode_df = pd.DataFrame(episode_rewards)
    trial_df = pd.DataFrame(trial_rewards)
    ax = episode_df.plot(title=title)
    ax = trial_df.plot(title=title2)
    ax.set_xlabel(xlabel)
    ax.set_ylabel(ylabel)
    plt.show()



if __name__ == '__main__':
    dqn = DQN()
    episode_rewards, trial_rewards = dqn.learn()
    dqn.plot_rewards("Lunar Lander-v2 Train", "Lunar Lander-v2 Test", "episodes", "score", episode_rewards, trial_rewards)




