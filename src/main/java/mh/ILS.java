package mh;

import java.util.Random;
import static javax.swing.WindowConstants.*;

/**
 *
 * @author diego
 */
public class ILS {

    public static final int MAX = 5000;
    public final int SEED;
    public Random rand;
    public Solucion[] solILS;
    public Lista[] convergencia;

    public ILS(int a) {
        SEED = a;
        rand = new Random(SEED);
        solILS = new Solucion[P2.NUMP];
        convergencia = new Lista[P2.NUMP];
        for (int i = 0; i < P2.NUMP; i++) {
            convergencia[i] = new Lista<Integer>();
        }
    }

//    public void ejecutarILS() {
//        for (int i = 0; i < P2.NUMP; i++) {
//            solILS[i] = ILS(i);
//            System.out.println(solILS[i].coste + "\t" + solILS[i].eval);
//            if (i == 2 && SEED == 333) {
//                Grafica g = new Grafica(convergencia[i], "ILS");
//                g.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//                g.setBounds(200, 350, 800, 400);
//                g.setTitle("ILS - P" + (i + 1) + " - S" + SEED);
//                g.setVisible(true);
//            }
//        }
//    }
//
//    public Solucion ILS(int tamP) {
//        int[] P = P2.P[tamP];
//        int ciu = P[0];
//        int cam = P[2];
//        int eval = 0;
//        int maxeval = MAX * ciu;
//        Lista listaPal = P2.listaPal.get(tamP);
//        Matriz listaDist = P2.listaDist.get(tamP);
//
//        Solucion inicial = Solucion.genRandom(cam, listaPal, rand);
//        inicial.coste = Solucion.funCoste(inicial, listaDist);
//        eval++;
//        inicial.eval = eval;
//        convergencia[tamP].add(inicial.coste);
//        Solucion candidata, mejor, elite;
//        candidata = inicial;
//        mejor = inicial;
//        elite = new Solucion(new Matriz(1, 1, 0));
//
//        while (eval < maxeval) {
//            while (mejor.coste >= candidata.coste) {
//                candidata = Solucion.gen4optAlt(cam, candidata, rand);
//                candidata.coste = Solucion.funCoste(candidata, listaDist);
//                eval++;
//                candidata.eval = eval;
//                if (candidata.eval % 5000 == 0) {
//                    convergencia[tamP].add(candidata.coste);
//                }
//                if (mejor.coste > candidata.coste) {
//                    mejor = candidata;
//                }
//            }
//            
//            if (elite.coste > mejor.coste) {
//                elite = mejor;
//            }
//            
//            candidata = Solucion.genMutacion(cam, elite, rand);
//            candidata.coste = Solucion.funCoste(candidata, listaDist);
//            eval++;
//            candidata.eval = eval;
//            convergencia[tamP].add(candidata.coste);
//            mejor = candidata;
//        }
//
//        return elite;
//    }
}
