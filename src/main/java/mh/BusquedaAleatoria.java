package mh;

import java.util.Random;
import static javax.swing.WindowConstants.*;

/**
 *
 * @author diego
 */
public class BusquedaAleatoria {

    public static final int MAX = 5000;
    public final int SEED;
    public Random rand;
    public Solucion[] solBA;
    public ListaD[] convergencia;

    public BusquedaAleatoria(int a) {
        SEED = a;
        rand = new Random(SEED);
        solBA = new Solucion[P2.NUMP];
        convergencia = new ListaD[P2.NUMP];
        for (int i = 0; i < P2.NUMP; i++) {
            convergencia[i] = new ListaD();
        }
    }

    public void ejecutarBA() {
        for (int i = 0; i < P2.NUMP; i++) {
            solBA[i] = BA(i);
            System.out.println(solBA[i].coste + "\t" + solBA[i].eval);
            if (i == 2 && SEED == 333) {
                Grafica g = new Grafica(convergencia[i], "BA");
                g.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                g.setBounds(200, 350, 800, 400);
                g.setTitle("BA - P" + (i + 1) + " - S" + SEED);
                g.setVisible(true);
            }
        }
    }

    public Solucion BA(int tamP) {
        Integer[] P = P2.P.get(tamP);
        int ciu = P[0];
        int cam = P[2];
        int eval = 0;
        int maxeval = MAX * ciu;
        ListaN listaPal = P2.listaPal.get(tamP);
        Matriz listaDist = P2.listaDist.get(tamP);

        Solucion mejor = Solucion.genRandom(cam, listaPal, rand);
        mejor.coste = Solucion.funCoste(mejor, listaDist);
        eval++;
        mejor.eval = eval;
        Double d = Double.valueOf(mejor.coste);
        convergencia[tamP].add(d);

        while (eval < maxeval) {
            Solucion siguiente = Solucion.genRandom(cam, listaPal, rand);
            siguiente.coste = Solucion.funCoste(siguiente, listaDist);
            eval++;
            siguiente.eval = eval;
            if (siguiente.eval % 5000 == 0) {
                d = Double.valueOf(siguiente.coste);
                convergencia[tamP].add(d);
            }
            if (mejor.coste > siguiente.coste) {
                mejor = siguiente;
            }
        }

        return mejor;
    }

}
