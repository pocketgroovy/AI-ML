import random
import numpy as np
import time
import pandas as pd
import matplotlib.pyplot as plt

CHOICE = [-1, 1]
SET_SEQUENCE_COUNT = 10

# def create_random_sequence():
#    	sequence = []
#    	curr_index = 2
# 	while curr_index >= 0 and curr_index < 5:
# 		step = [0,0,0,0,0]
# 		step[curr_index] = 1
# 		sequence.append(step)
# 		num = random.choice(CHOICE)
# 		curr_index = curr_index + num
# 	if curr_index == -1:
# 		sequence.append(0)
# 	elif curr_index == 5:
# 		sequence.append(1)
# 	return sequence

# def create_training_set():
# 	training_set = []
# 	for i in range(SET_SEQUENCE_COUNT):
# 		sequence = create_random_sequence()
# 		training_set.append(sequence)
# 	return training_set

def get_delta_weight(alpha, lamb, w, sub_seq, outcome):
	sum_step_delta_w = [0.0, 0.0, 0.0, 0.0, 0.0]
	for t in range(1, len(sub_seq)+1):
		p = diff_step_prob(t, w, sub_seq, outcome)
		step_weight = sum_previous_steps(t, sub_seq, lamb, w)
		p_diff = [p*i for i in step_weight]
		delta_w = [alpha*i for i in p_diff]
		sum_step_delta_w = np.add(sum_step_delta_w, delta_w)
	return sum_step_delta_w

def sum_previous_steps(t, x, lamb, step_weight):
	xx = x[0:t]
	kk = 1
	for k in xx:
		step_weight = np.add(step_weight, [(lamb**(t-kk))*i for i in k])
		kk+=1
	return step_weight

def diff_step_prob(t, w, x, outcome):
	if t == len(x):
		p2 = outcome
		p1 = np.dot(w,x[t-1])
	else:
		p2 = np.dot(w,x[t+1-1])
		p1 = np.dot(w,x[t-1])
	res = p2-p1
	return res

def RMSE(true_weights, pred_weights):
	res = np.sqrt(((np.subtract(true_weights, pred_weights))**2).mean(axis=0))
	return res

# # using absolute instead of squaring
# def RMSE(true_weights, pred_weights):
# 	res = np.sqrt((np.absolute(np.subtract(true_weights, pred_weights))).mean(axis=0))
# 	return res

def run_a_training_set(training_set, alpha, lamb, weight_vector, seq_update):
	sum_delta_w = [0.0, 0.0, 0.0, 0.0, 0.0]
	for seq in training_set:
		sub_seq = seq[0:len(seq)-1]
		outcome = seq[len(seq)-1]
		delta_w = get_delta_weight(alpha, lamb, weight_vector, sub_seq, outcome)
		if seq_update:
			weight_vector += delta_w
			sum_delta_w = weight_vector
		else:
			sum_delta_w = np.add(sum_delta_w, delta_w)
	return sum_delta_w

def average_error_for_all_training_sets(all_training_sets, alpha, lamb, weight_vector, true_weights, threshold, seq_update):
	error_list = []
	for training_set in all_training_sets:
		w_vec = []
		if seq_update:
			w_vec = one_training(training_set, alpha, lamb, weight_vector, seq_update)
		else:
			w_vec = repeat_training_til_converged(training_set, alpha, lamb, weight_vector, threshold, seq_update)
		error = RMSE(true_weights, w_vec)
		error_list.append(error)
	avg_error = np.mean(error_list)
	return avg_error

def repeat_training_til_converged(training_set, alpha, lamb, weight_vector, threshold, seq_update):
	diff_weight_vec = threshold+1
	while diff_weight_vec > threshold:
		diff_weight_vec = 0
		trained_delta_weight = run_a_training_set(training_set, alpha, lamb, weight_vector, seq_update)
		diff_weight_vec = np.max(np.absolute(trained_delta_weight), axis=0)
		weight_vector = np.add(weight_vector, trained_delta_weight)
	return weight_vector

def one_training(training_set, alpha, lamb, weight_vector, seq_update):
	trained_delta_weight = run_a_training_set(training_set, alpha, lamb, weight_vector, seq_update)
	return trained_delta_weight

def experiment_one(all_training_sets, alpha, lamb_vec, weight_vector, true_weights = [1/6.0,1/3.0,1/2.0,2/3.0,5/6.0], threshold=0.001):
	error_list_lamb = []
	for lamb in lamb_vec:
		error = average_error_for_all_training_sets(all_training_sets, alpha, lamb, weight_vector, true_weights, threshold, False)
		error_list_lamb.append(error)
	return error_list_lamb

def experiment_two(all_training_sets, alpha_vector, lamb_vec, weight_vector, true_weights = [1/6.0,1/3.0,1/2.0,2/3.0,5/6.0], threshold=0.001):
	error_alpha = []
	for alpha in alpha_vector:
		error_list_lamb = []
		for lamb in lamb_vec:
			error = average_error_for_all_training_sets(all_training_sets, alpha, lamb, weight_vector, true_weights, threshold, True)
			error_list_lamb.append(error)
		error_alpha.append(error_list_lamb)
	return error_alpha

def plot_data(df, xticks, ymin, ymax, xarange=7, title="Experiment One", xlabel="Lambda", ylabel="Error", legend_loc='upper left'):
    ax = df.plot(title=title)
    ax.set_xlabel(xlabel)
    ax.set_ylabel(ylabel)
    plt.xticks(np.arange(xarange), xticks)
    plt.ylim(ymin,ymax)
    plt.show()

def start_figure3(all_training_sets, alpha, lambda_vector, weight_vector, true_weights, threshold):
    error_list_exp1 = experiment_one(all_training_sets, alpha, lambda_vector, weight_vector, true_weights, threshold)
    df1 = pd.DataFrame({'Error':error_list_exp1})
    plot_data(df1, xticks=lambda_vector, ymin=0.1, ymax=0.18, title="figure 3")

def start_figure4(all_training_sets, alpha_vector, lambda_vector, weight_vector, true_weights):
    error_list_exp2 = experiment_two(all_training_sets, alpha_vector, lambda_vector, weight_vector, true_weights=true_weights)
    df2 = pd.DataFrame(error_list_exp2)
    df2.columns = lambda_vector
    plot_data(df2, xticks=alpha_vector, ymin=0.3, ymax=0.6, xarange=13, title="figure 4 (absolute)", xlabel='alpha')

def start_figure5(all_training_sets, alpha_vector, lambda_vector, weight_vector, true_weights, threshold=0.001, seq_update=True):
    error_list_exp2 = experiment_two(all_training_sets, alpha_vector, lambda_vector, weight_vector, true_weights=true_weights)
    df3 = pd.DataFrame(error_list_exp2)
    df3.columns = lambda_vector
    best_list = []
    for col in lambda_vector:
    	idx = df3[col].idxmin()
    	best_list.append(df3.iloc[idx][col])
    df4 = pd.DataFrame({'Error':best_list})
    plot_data(df4, xticks=lambda_vector3, ymin=0.12, ymax=0.20, xarange=11, title="figure 5", ylabel="Error Using Best alpha")


if __name__ == "__main__":
    start = time.time()

    ## create test data
    # training_data = []
    # for i in range(100):
    # 	training_set = create_training_set()
    # 	training_data.append(training_set)

    ## writing to a file
    # with open("test_seq6", "w") as f:
    # 	f.write(str(training_data))

    # reading the test data
    from ast import literal_eval
    with open('test_seq5') as inp:
    	for line in inp:
    		all_training_sets = literal_eval(line)


    alpha = 0.02
    alpha_vector = [0.0, 0.05, 0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6]
    lambda_vector = [0.0, 0.1, 0.3, 0.5, 0.7, 0.9, 1.0]
    lambda_vector2 = [0.0, 0.3, 0.8, 1.0]
    lambda_vector3 = [0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0]
    weight_vector = [0.5, 0.5, 0.5, 0.5, 0.5]
    true_weights = [1/6.0,1/3.0,1/2.0,2/3.0,5/6.0]
    threshold = 0.003

    start_figure3(all_training_sets, alpha, lambda_vector, weight_vector, true_weights, threshold)
    start_figure4(all_training_sets, alpha_vector, lambda_vector2, weight_vector, true_weights)
    start_figure5(all_training_sets, alpha_vector, lambda_vector3, weight_vector, true_weights)

    end = time.time()
    print(end - start)