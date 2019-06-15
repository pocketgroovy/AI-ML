from Environment import SoccerField
import random
from random import shuffle
from random import randint
import numpy as np
from tqdm import tqdm
import pandas as pd
import matplotlib.pyplot as plt
import math
from LinPrg import LP


# translate coordinate(x,y) to a cell number
def map_to_state(grid_state,loc):
    return grid_state[loc[0]][loc[1]]

# translate a cell number to coordinate(x,y)
def map_to_loc(grid_state, state):
    total_cell = len(grid_state[0]) * len(grid_state)
    if state < 0 or state >= total_cell:
        raise ValueError('state number is wrong')
    if state in range(0, len(grid_state[0])):
        return [0,state]
    else:
        return [1,state%len(grid_state[0])]

def map_to_action(direction):
    return {
        'n': 0,
        'e': 1,
        's': 2,
        'w': 3,
        'd': 4
    }[direction]

def map_to_dir(action):
    return {
        0:'n',
        1:'e',
        2:'s',
        3:'w',
        4:'d'
    }[action]

def select_action(state_a, state_b, ball, q, epsilon, action_num):
  if random.random() < epsilon:
        return randint(0, action_num-1)
  else:
        return (np.argmax(q[state_a][state_b][ball][0]))

def create_player_action_state(player_num, action_num):
  action_a = []
  for i in range(action_num):
   a = [1.] * action_num 
   action_a.append(a)
  return action_a

def create_q_table(num_state, ball_state_num, player_num, action_num):
  p1_state = []
  for i in range(num_state):
    p2_state = []
    for j in range(num_state):
      ball_state = []
      for ball in range(ball_state_num):
        players_action_state = create_player_action_state(player_num, action_num)
        ball_state.append(players_action_state)
      p2_state.append(ball_state)
    p1_state.append(p2_state)
  return p1_state

def create_q_counter(num_state, ball_state_num, action_num):
  p1_state = []
  for i in range(num_state):
    p2_state = []
    for j in range(num_state):
      ball_state = []
      for ball in range(ball_state_num):
        action = [1] * action_num 
        ball_state.append(action)
      p2_state.append(ball_state)
    p1_state.append(p2_state)
  return p1_state

def get_v_value(Q, state_a, state_b, whose_ball):
  action_north = map_to_action('n')
  action_south = map_to_action('s')
  action_east = map_to_action('e')
  action_west = map_to_action('w')
  action_stick = map_to_action('d')

  lp = LP();
  P1 = Q[state_a][state_b][whose_ball][action_north][action_north]
  P2 = Q[state_a][state_b][whose_ball][action_north][action_south]
  P3 = Q[state_a][state_b][whose_ball][action_north][action_east]
  P4 = Q[state_a][state_b][whose_ball][action_north][action_west]
  P5 = Q[state_a][state_b][whose_ball][action_north][action_stick]
  P6 = Q[state_a][state_b][whose_ball][action_south][action_north]
  P7 = Q[state_a][state_b][whose_ball][action_south][action_south]
  P8 = Q[state_a][state_b][whose_ball][action_south][action_east]
  P9 = Q[state_a][state_b][whose_ball][action_south][action_west]
  P10 = Q[state_a][state_b][whose_ball][action_south][action_stick]
  P11 = Q[state_a][state_b][whose_ball][action_east][action_north]
  P12 = Q[state_a][state_b][whose_ball][action_east][action_south]
  P13 = Q[state_a][state_b][whose_ball][action_east][action_east]
  P14 = Q[state_a][state_b][whose_ball][action_east][action_west]
  P15 = Q[state_a][state_b][whose_ball][action_east][action_stick]
  P16 = Q[state_a][state_b][whose_ball][action_west][action_north]
  P17 = Q[state_a][state_b][whose_ball][action_west][action_south]
  P18 = Q[state_a][state_b][whose_ball][action_west][action_east]
  P19 = Q[state_a][state_b][whose_ball][action_west][action_west]
  P20 = Q[state_a][state_b][whose_ball][action_west][action_stick]
  P21 = Q[state_a][state_b][whose_ball][action_stick][action_north]
  P22 = Q[state_a][state_b][whose_ball][action_stick][action_south]
  P23 = Q[state_a][state_b][whose_ball][action_stick][action_stick]
  P24 = Q[state_a][state_b][whose_ball][action_stick][action_east]
  P25 = Q[state_a][state_b][whose_ball][action_stick][action_west]

  v = lp.min_max_strategy(P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, \
        P11, P12, P13, P14, P15, P16, P17, P18,P19, P20, P21, P22, P23,P24,P25)
  return v

def q_learning(env, 
               gamma = 0.9,
               epsilon = 1,
               alpha = 0.02,
               min_epsilon = 0.02,
               min_alpha = 0.001,
               num_steps=1000000):
    action_south = map_to_action('s')
    action_stick = map_to_action('d')
    grid = env.get_grid()
    num_action = env.get_action_num()
    num_state = env.get_cell_num()
    grid_state = env.get_grid_state()

    players = env.get_players()
    player_num = len(players)
    ball_state_num = player_num
    player_a = 0
    player_b = 1
    diff = 0
    prev = 0

    # Q[[state a][state b][whose ball][a's action][b's action]]
    Qa = create_q_table(num_state, ball_state_num, player_num, num_action)
    Qb = create_q_table(num_state, ball_state_num, player_num, num_action)
    # q_counter[[state a][state b][[whose ball][action]]
    q_pair_counter_a = create_q_counter(num_state,ball_state_num, num_action) 
    q_pair_counter_b = create_q_counter(num_state,ball_state_num, num_action)
    Qa_episode_error_data = []
    Qa_episode_error_steps = []
    done = True
    for t in tqdm(range(num_steps)):
      if done:
        done = False
        env = env.reset()
        loc_a = env.get_a_loc() # [0,2]
        loc_b = env.get_b_loc() # [0,1]
        init_ball = env.whose_ball(loc_b)
        whose_ball = init_ball

        init_a = map_to_state(grid_state, loc_a) # 2
        init_b = map_to_state(grid_state, loc_b) # 1
        state_a = init_a
        state_b = init_b
      alpha = max(alpha-1e-10, min_alpha) # decaying alpha

      shuffle(players) # randomly choose starting player
      error_data = []
      for player in players:
          q_pair_counter = q_pair_counter_a if player == 'a' else q_pair_counter_b
          Q = Qa if player == 'a' else Qb
          action = select_action(state_a, state_b, whose_ball, Q, epsilon, num_action)
          who_had_ball = whose_ball
          count = q_pair_counter[state_a][state_b][who_had_ball][action] + 1
          q_pair_counter[state_a][state_b][who_had_ball][action] = count

          direction = map_to_dir(action)
          a_new_loc, b_new_loc, a_reward, b_reward, done = env.step(direction, player)

          whose_ball = env.whose_ball(a_new_loc)
          if player == 'a':
              action_a = action
          else:
              action_b = action

      state_prime_a = map_to_state(grid_state, a_new_loc)
      state_prime_b = map_to_state(grid_state, b_new_loc)
      if state_a == state_prime_a:
        action_a = action_stick
      if state_b == state_prime_b:
        action_b = action_stick
      if done:
        a_reward = a_reward
        b_reward = b_reward
      else: 
        vA = get_v_value(Qa, state_prime_a, state_prime_b, whose_ball)
        vB = get_v_value(Qb, state_prime_a, state_prime_b, whose_ball)
        if vA['x'] != None and vB['x'] != None:
          a_reward = (1-gamma)* a_reward + gamma * max(vA['x'])
          b_reward = (1-gamma)* b_reward + gamma * max(vB['x'])

      Qa_value = Qa[init_a][init_b][init_ball][action_south][action_stick]
      old_value = Qa_value 


      alpha_a = 1.0/(q_pair_counter_a[state_a][state_b][who_had_ball][action_a] if q_pair_counter_a[state_a][state_b][who_had_ball][action_a] != 0 else 1)
      alpha_b = 1.0/(q_pair_counter_b[state_b][state_b][who_had_ball][action_b] if q_pair_counter_b[state_b][state_b][who_had_ball][action_b] != 0 else 1)
      alpha_a = max(alpha_a, min_alpha)
      alpha_b = max(alpha_b, min_alpha)
      Qa[state_a][state_b][who_had_ball][action_a][action_b] = (1-alpha) * Qa[state_a][state_b][who_had_ball][action_a][action_b] + alpha * a_reward
      Qb[state_a][state_b][who_had_ball][action_b][action_a] = (1-alpha) * Qb[state_a][state_b][who_had_ball][action_b][action_a] + alpha * b_reward


      Qa_value = Qa[init_a][init_b][init_ball][action_south][action_stick]
      new_value = Qa_value      
      diff = math.sqrt((new_value - old_value)**2)

      state_a = state_prime_a
      state_b = state_prime_b
      if diff > 0:
        Qa_episode_error_steps.append(t)
        Qa_episode_error_data.append(diff)


    return Qa_episode_error_data, Qa_episode_error_steps

def plot_errors(title, xlabel, ylabel, error, steps):
    plt.title(title)
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    plt.plot(steps, error)
    plt.show()

if __name__ == '__main__':
    grid_h = 2
    grid_w = 4
    ball_loc = [0,1]
    b_loc = [0,1]
    a_loc = [0,2]
    env = SoccerField(Debug=False)
    Qa_episode_error_data, Qa_episode_error_steps = q_learning(env, num_steps=1000000)
    plot_errors("Foe Q", "Simulation Iteration", "Q-Value Difference", Qa_episode_error_data, Qa_episode_error_steps)
