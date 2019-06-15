class SoccerField():
    def __init__(self, grid_h=2, grid_w=4, ball_loc=[0,1], b_loc=[0,1], a_loc=[0,2], players =['a','b'], action = ['n', 's', 'e', 'w', 'd'], Debug=False):
        self.Debug = Debug
        self.h = grid_h
        self.w = grid_w
        self.empty_pos = '-'
        self.grid = [['-' for x in range(self.w)] for y in range(self.h)] 
        self.cell_num = grid_h * grid_w
        self.a_loc = a_loc
        self.b_loc = b_loc
        self.__move_player_loc(b_loc, b_loc, 'b')
        self.__move_player_loc(a_loc, a_loc, 'a')
        self.ball_pos = ball_loc
        self.action = action
        self.players = players

        self.ball_grid = [[0 for x in range(self.w)] for y in range(self.h)] 
        self.__move_ball_loc(ball_loc, ball_loc) # reset to 0 and set 1

    def __move_player_loc(self, old_player_loc, new_player_loc, player):
        self.grid[old_player_loc[0]][old_player_loc[1]] = self.empty_pos
        self.grid[new_player_loc[0]][new_player_loc[1]] = player
        if player == 'a':
            self.a_loc = new_player_loc
        else:
            self.b_loc = new_player_loc

    def __move_ball_loc(self, old_loc, new_loc):
        self.ball_grid[old_loc[0]][old_loc[1]] = 0
        self.ball_grid[new_loc[0]][new_loc[1]] = 1 # if ball at location, 1 otherwise 0
        self.ball_pos = new_loc
        
    def get_players(self):
        return self.players

    def whose_ball(self, b_loc):
        ball_has = 1 if self.ball_grid[b_loc[0]][b_loc[1]] == 1 else 0
        return ball_has

    def get_cell_num(self):
        return self.cell_num

    def get_grid(self):
        return self.grid

    def get_grid_state(self):
        grid = []
        num = 0
        for i in range(2):
         state = []
         for j in range(4):
           state.append(num)
           num+=1
         grid.append(state)
        return grid

    def get_action_num(self):
        return len(self.action)

    def get_ball_loc(self):
        return self.ball_grid

    def get_a_loc(self):
        return self.a_loc

    def get_b_loc(self):
        return self.b_loc

    def __get_new_location(self, action, location):
        loc_row = location[0]
        loc_col = location[1]

        if action == 'n':
            loc_row = loc_row - 1
        elif action == 's':
            loc_row = loc_row + 1
        elif action == 'e':
            loc_col = loc_col + 1
        elif action == 'w':
            loc_col = loc_col -1

        if loc_col >= self.w:
            loc_col = self.w-1
        elif loc_col < 0:
            loc_col = 0

        if loc_row >= self.h:
            loc_row = self.h-1
        elif loc_row < 0:
            loc_row = 0

        adjusted_location = [loc_row, loc_col]
        return adjusted_location

    # if ball is in A goal, it doesn't matter who had the ball
    # it will be scored according to the goal
    def __check_reward(self, position):
        done = False
        a_reward = 0
        b_reward = 0
        if self.ball_grid[position[0]][position[1]] == 1:
            if position[1] == 0:
                a_reward = 100
                b_reward = -100
                done = True
                if self.Debug:
                    print("A's goal")
            elif position[1] == self.w-1:
                a_reward = -100
                b_reward = 100
                done = True
                if self.Debug:
                    print("B's goal")

        return a_reward, b_reward, done

    def step(self, action, player):
        if self.Debug:
            print("{}'s move to {}".format(player, action))
        curr_player_loc = self.a_loc if player == 'a' else self.b_loc
        opponent = 'a' if player != 'a' else 'b'
        ball_pos = []
        curr_new_position = self.__get_new_location(action, curr_player_loc)

        # if current player moves to where another player is
        if self.grid[curr_new_position[0]][curr_new_position[1]] == opponent:
            if self.Debug:
                print("opponent found at new location") 
            # and the current player has a ball 
            # the ball will be switched to the another player and the current player stays back
            if self.ball_grid[curr_player_loc[0]][curr_player_loc[1]] == 1:
                if self.Debug:
                    print("{} has a ball before move".format(player))       
                self.__move_ball_loc(curr_player_loc, curr_new_position)
                if self.Debug:
                    print("{} has a ball after move".format(opponent))

            # either switched or already opponent had, the ball should be at the new position regardless
            a_reward, b_reward, done = self.__check_reward(self.ball_pos)
            if self.Debug:
                print("check reward and ball pos after opponent found")

        else:
            if self.grid[curr_new_position[0]][curr_new_position[1]] == self.empty_pos:
                if self.Debug:
                    print("nobody found at new location")
                # if nobody in the new position, just move along with a ball if in possession.
                self.__move_player_loc(curr_player_loc, curr_new_position, player) #empty the position just left
                if self.ball_grid[curr_player_loc[0]][curr_player_loc[1]] == 1:
                    if self.Debug:
                        print("{} has a ball with new location".format(player))     
                    # print("move")
                    self.__move_ball_loc(curr_player_loc, curr_new_position)
            a_reward, b_reward, done = self.__check_reward(self.ball_pos)
            if self.Debug:
                print("check reward and ball pos after move to new location")
        if self.Debug:
            print(self.a_loc, self.b_loc, a_reward, b_reward, done, self.ball_grid)
        return self.a_loc, self.b_loc, a_reward, b_reward, done
    
    def reset(self):
            env = SoccerField(Debug=self.Debug)
            if self.Debug:
                print("reset..")
            return env

    

    def test_1(self):
        print("1")
        print(self.get_ball_loc())
        print("2")
        print(self.step('e', 'b'))
        print(self.get_ball_loc())
        print("3")
        print(self.step('e', 'a'))
        print(self.get_ball_loc())

    def test_2(self):
        print("1")
        print(self.get_ball_loc())
        print("2")
        print(self.step('w', 'b'))
        print(self.get_ball_loc())
        print("3")
        print(self.step('e', 'a'))
        print(self.get_ball_loc())

    def test_3(self):
        print("1")
        print(self.get_ball_loc())
        print("2")
        print(self.step('s', 'b'))
        print(self.get_ball_loc())
        print("3")
        print(self.step('w', 'a'))
        print(self.get_ball_loc())  
        print("4")
        print(self.step('e', 'b'))
        print(self.get_ball_loc())
        print("5")
        print(self.step('s', 'a'))
        print(self.get_ball_loc())  
        print("6")
        print(self.step('e', 'b'))
        print(self.get_ball_loc())
        print("7")  
        print(self.step('e', 'a'))
        print(self.get_ball_loc())

    def test_4(self):
        print("1")
        print(self.get_ball_loc())
        print("2")
        print(self.step('w', 'a'))
        print(self.get_ball_loc())
        print("3")
        print(self.step('d', 'b'))
        print(self.get_ball_loc())  
        print("4")
        print(self.step('w', 'b'))
        print(self.get_ball_loc())  


if __name__ == '__main__':
    grid_h = 2
    grid_w = 4
    ball_loc = [0,1]
    b_loc = [0,1]
    a_loc = [0,2]
    action = ['n', 's', 'e', 'w', 'd']
    env = SoccerField(grid_h, grid_w, ball_loc, b_loc, a_loc,action, True)
    # env.test_1() # B swicthed to A a ball and A goal to B's
    # env = env.reset()
    # env.test_2() # B goal to A's and A went to B's without ball
    # env = env.reset()
    # env.test_3() # B goal all the way a just follow
    env = env.reset()
    env.test_4() # 
