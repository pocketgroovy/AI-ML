from cvxopt import matrix, solvers
import numpy as np

class LP():
    def __init__(self):
        solvers.options['show_progress'] = False
        solvers.options['glpk'] = {'LPX_K_MSGLEV': 0, 'msg_lev': "GLP_MSG_OFF"}

    def min_max_strategy(self, P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, \
        P11, P12, P13, P14, P15, P16, P17, P18,P19, P20, P21, P22, P23,P24,P25):
        G1_1 = P1 - P1
        G1_2 = P6 - P2
        G1_3 = P11 - P3
        G1_4 = P16 - P4
        G1_5 = P21 - P5
        G2_1 = P2 - P6
        G2_2 = P7 - P7
        G2_3 = P12 - P8
        G2_4 = P17 - P9
        G2_5 = P22 - P10
        G3_1 = P3 - P11
        G3_2 = P8 - P12
        G3_3 = P13 - P13
        G3_4 = P18 - P14
        G3_5 = P23 - P15
        G4_1 = P4 - P16 
        G4_2 = P9 - P17
        G4_3 = P14 - P18
        G4_4 = P19 - P19
        G4_5 = P24 - P20
        G5_1 = P5 - P21
        G5_2 = P10 - P22
        G5_3 = P15 - P23
        G5_4 = P20 - P24
        G5_5 = P25 - P25
        constraints1 = np.array([G1_1, G1_2, G1_3, G1_4, G1_5])
        constraints2 = np.array([G2_1, G2_2, G2_3, G2_4, G2_5])
        constraints3 = np.array([G3_1, G3_2, G3_3, G3_4, G3_5])
        constraints4 = np.array([G4_1, G4_2, G4_3, G4_4, G4_5])
        constraints5 = np.array([G5_1, G5_2, G5_3, G5_4, G5_5])
        constraints6 = np.array([-P1, -P2, -P3, -P4, -P5]) # action N
        constraints7 = np.array([-P6, -P7, -P8, -P9, -P10]) # action S
        constraints8 = np.array([-P11, -P12, -P13, -P14, -P15]) # action E
        constraints9 = np.array([-P16, -P17, -P18, -P19, -P20]) # action W
        constraints10 = np.array([-P21, -P22, -P23,-P24, -P25])# action Stick

        c = matrix([1., 1., 1., 1.,1.,1., 1., 1., 1.,1.])
        G = matrix([constraints1.tolist(), constraints2.tolist(), constraints3.tolist(), constraints4.tolist(), constraints5.tolist(), \
            constraints6.tolist(), constraints7.tolist(), constraints8.tolist(), constraints9.tolist(), constraints10.tolist()])
        h = matrix([0.,0.,0.,0.,0.])
        A = matrix([[1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.]])
        b = matrix(1.)

        res = {'x':[1./5,1./5,1./5,1./5,1./5]}
        try:
            res =  solvers.lp(c, G, h, A, b, solver='glpk')

        except Exception as e:
            print("exception thrown {}".format(e))
        return res
    



