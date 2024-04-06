package mh;

import java.util.Random;
import static javax.swing.WindowConstants.*;

/**
 *
 * @author diego
 */
public class BusquedaLocal {

    public final int SEED;
    public Random rand;
    public Solucion[] solBL;
    public Lista[] convergencia;

    public BusquedaLocal(int a) {
        SEED = a;
        rand = new Random(SEED);
        solBL = new Solucion[P2.NUMP];
        convergencia = new Lista[P2.NUMP];
        for (int i = 0; i < P2.NUMP; i++) {
            convergencia[i] = new Lista<Integer>();
        }
    }

    public void ejecutarBL() {
        for (int i = 0; i < P2.NUMP; i++) {
            String muestra = P2.MAX + " * n";
            solBL[i] = BL(i);
            System.out.println(solBL[i].coste + "\t" + solBL[i].eval);
            if (i == 2 && SEED == 333) {
                GraficaS g = new GraficaS(convergencia[i], "BL", muestra);
                g.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                g.setBounds(200, 350, 800, 400);
                g.setTitle("BL - P" + (i + 1) + " - S" + SEED);
                g.setVisible(true);
            }
        }
    }

    public Solucion BL(int tamP) {
        int[] P = P2.P[tamP];
        int ciu = P[0];
        int cam = P[2];
        int eval = 0;
        int maxeval = P2.MAX * ciu;
        Lista listaPal = P2.listaPal.get(tamP);
        Matriz listaDist = P2.listaDist.get(tamP);

        Solucion inicial = Solucion.genRandom(cam, listaPal, rand);
        inicial.coste = Solucion.funCoste(inicial, listaDist);
        eval++;
        inicial.eval = eval;
        convergencia[tamP].add(inicial.coste);

        Solucion actual = inicial;
        while (eval < maxeval) {
            Solucion siguiente = Solucion.gen4opt(cam, actual, rand);
            siguiente.coste = Solucion.funCoste(siguiente, listaDist);
            eval++;
            siguiente.eval = eval;
            if (siguiente.eval % P2.MAX == 0) {
                convergencia[tamP].add(siguiente.coste);
            }
            if (actual.coste > siguiente.coste) {
                actual = siguiente;
            }
        }

        return actual;
    }

    public static Solucion BL(Random rand, int tamP, int maxiter, Solucion inicial, Lista<Integer> convergencia) {
        int[] P = P2.P[tamP];
        int cam = P[2];
        int iter = 0;
        int eval = inicial.eval;
        Matriz listaDist = P2.listaDist.get(tamP);

        inicial.coste = Solucion.funCoste(inicial, listaDist);
        iter++;
        eval++;
        inicial.eval = eval;
        convergencia.add(inicial.coste);
        int muestra = maxiter / P2.RESTART;

        Solucion actual = inicial;
        while (iter < maxiter) {
            Solucion siguiente = Solucion.gen4opt(cam, actual, rand);
            siguiente.coste = Solucion.funCoste(siguiente, listaDist);
            iter++;
            eval++;
            siguiente.eval = eval;
            if (siguiente.eval % muestra == 0) {
                convergencia.add(siguiente.coste);
            }
            if (actual.coste > siguiente.coste) {
                actual = siguiente;
            }
        }

        actual.lasteval = eval;
        return actual;
    }

    public static Solucion BLF(Random rand, int tamP, int maxeval, Solucion inicial, Lista<Integer> convergencia) {
        int[] P = P2.P[tamP];
        int cam = P[2];
        int eval = inicial.eval;
        Matriz listaDist = P2.listaDist.get(tamP);

        inicial.coste = Solucion.funCoste(inicial, listaDist);
        eval++;
        inicial.eval = eval;
        int muestra = P2.MAX;

        Solucion actual = inicial;
        Solucion siguiente = inicial;
        while (eval < maxeval && actual.coste >= siguiente.coste) {
            siguiente = Solucion.gen4opt(cam, actual, rand);
            siguiente.coste = Solucion.funCoste(siguiente, listaDist);
            eval++;
            siguiente.eval = eval;
            if (siguiente.eval % muestra == 0) {
                convergencia.add(siguiente.coste);
            }
            if (actual.coste > siguiente.coste) {
                actual = siguiente;
            }
        }

        actual.lasteval = eval;
        return actual;
    }

}
