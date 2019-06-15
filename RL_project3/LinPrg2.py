from cvxopt import matrix, solvers
import numpy as np

class LP():
    def __init__(self):
        solvers.options['show_progress'] = False
        solvers.options['glpk'] = {'LPX_K_MSGLEV': 0, 'msg_lev': "GLP_MSG_OFF"}

    def min_max_strategy(self, P1, P2, P3, P4, P5, P6, P7, P8, P9, P10, \
        P11, P12, P13, P14, P15, P16, P17, P18,P19, P20, P21, P22, P23,P24,P25):
        Na_G1 = np.array([(P1 - P6), (P1 - P11), (P1 - P16), (P1 - P21)])
        Na_G2 = np.array([(P2 - P7), (P2 - P12), (P2 - P17), (P2 - P22)])
        Na_G3 = np.array([(P3 - P8), (P3 - P13), (P3 - P18), (P3 - P23)])
        Na_G4 = np.array([(P4 - P9), (P4 - P14), (P4 - P19), (P4 - P24)])
        Na_G5 = np.array([(P5 - P10),(P5 - P15), (P5 - P20), (P5 - P25)])

        Sa_G1 = np.array([(P6 - P1), (P6 - P11), (P6 - P16), (P6 - P21)])
        Sa_G2 = np.array([(P7 - P2), (P7 - P12), (P7 - P17), (P7 - P22)])
        Sa_G3 = np.array([(P8 - P3), (P8 - P13), (P8 - P18), (P8 - P23)])
        Sa_G4 = np.array([(P9 - P4), (P9 - P14), (P9 - P19), (P9 - P24)])
        Sa_G5 = np.array([(P10 -P5), (P10- P15), (P10- P20), (P10- P25)])

        Ea_G1 = np.array([(P11-P1), (P11- P6), (P11 - P16), (P11 - P21)])
        Ea_G2 = np.array([(P12-P2), (P12- P7), (P12 - P17), (P12 - P22)])
        Ea_G3 = np.array([(P13-P3), (P13- P8), (P13 - P18), (P13 - P23)])
        Ea_G4 = np.array([(P14-P4), (P14- P9), (P14 - P19), (P14 - P24)])
        Ea_G5 = np.array([(P15-P5), (P15-P10), (P15 - P20), (P15 - P25)])

        Wa_G1 = np.array([(P16-P1), (P16-P6), (P16-P11), (P16-P21)])
        Wa_G2 = np.array([(P17-P2), (P17-P7), (P17-P12), (P17-P22)])
        Wa_G3 = np.array([(P18-P3), (P18-P8), (P18-P13), (P18-P23)]) 
        Wa_G4 = np.array([(P19-P4), (P19-P9), (P19-P14), (P19-P24)])
        Wa_G5 = np.array([ (P20-P5), (P20-P10),(P20-P15), (P20-P25)])

        Da_G1 = np.array([(P21-P1), (P21-P6), (P21-P11), (P21-P16)])
        Da_G2 = np.array([ (P22-P2),  (P22-P7), (P22-P12), (P22-P17)])
        Da_G3 = np.array([ (P23-P3), (P23-P8), (P23-P13), (P23-P18)])
        Da_G4 = np.array([ (P24-P4), (P24-P9), (P24-P14),  (P24-P19)])
        Da_G5 = np.array([ (P25-P5), (P25-P10),(P25-P15), (P25-P20)])

        Nb_G1 = np.array([(P1 - P2), (P1 - P3), (P1 - P4), (P1 - P5)])
        Nb_G2 = np.array([(P6 - P7), (P6 - P8), (P6 - P9), (P6 - P10)])
        Nb_G3 = np.array([(P11- P12),(P11-P13), (P11-P14), (P11-P15)])
        Nb_G4 = np.array([(P16-P17), (P16-P18), (P16-P19), (P16-P20)])
        Nb_G5 = np.array([(P21- P22),(P21- P23),(P21- P24),(P21- P25)])

        Sb_G1 = np.array([(P2 - P1), (P2 - P3), (P2 - P4), (P2 - P5)])
        Sb_G2 = np.array([(P7- P6), (P7 - P8), (P7 - P9), (P7 - P10)])
        Sb_G3 = np.array([(P12-P11), (P12-P13), (P12-P14), (P12-P15)])
        Sb_G4 = np.array([(P17-P16), (P17-P18), (P17-P19), (P17-P20)])
        Sb_G5 = np.array([(P22- P21),(P22-P23), (P22-P24), (P22-P25)])

        Eb_G1 = np.array([(P3 - P1), (P3 - P2), (P3 - P4), (P3 - P5) ])
        Eb_G2 = np.array([(P8 - P6), (P8 - P7), (P8 - P9), (P8 -P10) ])
        Eb_G3 = np.array([ (P13-P11), (P13-P12), (P13-P14), (P13-P15) ])
        Eb_G4 = np.array([(P18-P16), (P18-P17), (P18-P19), (P18-P20)])
        Eb_G5 = np.array([(P23-P21), (P23-P22), (P23-P24), (P23-P25)])

        Wb_G1 = np.array([ (P4 - P1), (P4 - P2), (P4 - P3), (P4 - P5)])
        Wb_G2 = np.array([ (P9 - P6),(P9 - P7), (P9 - P8), (P9 - P10)])
        Wb_G3 = np.array([(P14-P11), (P1 -P12), (P14-P13), (P14-P15)])
        Wb_G4 = np.array([ (P19-P16), (P19-P17), (P19-P18), (P19-P20)])
        Wb_G5 = np.array([(P24-P21), (P24-P22), (P24- P23), (P24 - P25)])

        Db_G1 = np.array([(P5 - P1), (P5 - P2), (P5 - P3), (P5 - P4)])
        Db_G2 = np.array([(P10- P6), (P10- P7), (P10- P8), (P10- P9)])
        Db_G3 = np.array([(P15-P11), (P15-P12), (P15-P13), (P15-P14)])
        Db_G4 = np.array([(P20-P16), (P20-P17), (P20-P18), (P20-P19)])
        Db_G5 = np.array([(P25-P21), (P25-P22), (P25-P23), (P25-P24)])


        x1 = (Na_G1 + Nb_G1) * -1
        x2 = (Na_G2 + Nb_G2) * -1
        x3 = (Na_G3 + Nb_G3) * -1
        x4 = (Na_G4 + Nb_G4) * -1
        x5 = (Na_G5 + Nb_G5) * -1
        X6 = (Sa_G1 + Sb_G1) * -1
        x7 = (Sa_G2+ Sb_G2) * -1
        x8 = (Sa_G3+ Sb_G3) * -1
        x9 = (Sa_G4+ Sb_G4) * -1
        x10 = (Sa_G5+ Sb_G5) * -1
        x11 = (Ea_G1+ Eb_G1) * -1
        x12 = (Ea_G2+ Eb_G2) * -1 
        x13 = (Ea_G3+ Eb_G3) * -1
        x14 = (Ea_G4+ Eb_G4) * -1 
        x15 = (Ea_G5+ Eb_G5) * -1
        x16 = (Wa_G1+ Wb_G1) * -1
        x17 = (Wa_G2+ Wb_G2) * -1
        x18 = (Wa_G3+ Wb_G3) * -1 
        x19 = (Wa_G4+ Wb_G4) * -1
        x20 = (Wa_G5+ Wb_G5) * -1
        x21 = (Da_G1+ Db_G1) * -1
        x22 = (Da_G2+ Db_G2) * -1
        x23 = (Da_G3+ Db_G3) * -1
        x24 = (Da_G4+ Db_G4) * -1
        x25 = (Da_G5+ Db_G5) * -1


        # print("x1 {}".format(x1))

        c = matrix([1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1.])


        G = matrix([x1.tolist(),x2.tolist(),x3.tolist(),x4.tolist(),x5.tolist(),X6.tolist(),x7.tolist(),x8.tolist(),x9.tolist(),x10.tolist(), \
            x11.tolist(),x12.tolist(),x13.tolist(),x14.tolist(),x15.tolist(),x16.tolist(),x17.tolist(),x18.tolist(),x19.tolist(),x20.tolist(), \
            x21.tolist(),x22.tolist(),x23.tolist(),x24.tolist(),x25.tolist()])

        h = matrix([0., 0., 0., 0.])
        A = matrix([[1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], [1.], \
            [1.], [1.], [1.], [1.], [1.], [1.], [1.]])
        b = matrix(1.)

        res = {'x':[1./5,1./5,1./5,1./5,1./5]}
        try:
            res =  solvers.lp(c, G, h, A, b, solver='glpk')

        except Exception as e:
            print("exception thrown {}".format(e))
        return res
    



