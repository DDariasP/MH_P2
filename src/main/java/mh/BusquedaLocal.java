package mh;

import java.util.Random;
import static javax.swing.WindowConstants.*;

/**
 *
 * @author diego
 */
public class BusquedaLocal {

    public static final int MAX = 5000;
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
            solBL[i] = BL(i);
            System.out.println(solBL[i].coste + "\t" + solBL[i].eval);
            if (i == 2 && SEED == 333) {
                Grafica g = new Grafica(convergencia[i], "BL");
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
        int maxeval = MAX * ciu;
        Lista listaPal = P2.listaPal.get(tamP);
        Matriz listaDist = P2.listaDist.get(tamP);

        Solucion mejor = Solucion.genRandom(cam, listaPal, rand);
        mejor.coste = Solucion.funCoste(mejor, listaDist);
        eval++;
        mejor.eval = eval;
        convergencia[tamP].add(mejor.coste);

        while (eval < maxeval) {
            Solucion siguiente = Solucion.gen4optAlt(cam, mejor, rand);
            siguiente.coste = Solucion.funCoste(siguiente, listaDist);
            eval++;
            siguiente.eval = eval;
            if (siguiente.eval % 5000 == 0) {
                convergencia[tamP].add(siguiente.coste);
            }
            if (mejor.coste > siguiente.coste) {
                mejor = siguiente;
            }
        }

        return mejor;
    }

}
