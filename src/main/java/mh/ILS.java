package mh;

import java.util.Random;
import static javax.swing.WindowConstants.*;

/**
 *
 * @author diego
 */
public class ILS {

    public final int SEED;
    public Random rand;
    public Solucion[] solILS;
    public Lista[][] convergencia;
    public Lista<Solucion> listaElite;

    public ILS(int a) {
        SEED = a;
        rand = new Random(SEED);
        solILS = new Solucion[P2.NUMP];
        convergencia = new Lista[P2.NUMP][P2.RESTART];
        for (int i = 0; i < P2.NUMP; i++) {
            for (int j = 0; j < P2.RESTART; j++) {
                convergencia[i][j] = new Lista<Integer>();
            }
        }
    }

    public void ejecutarBLF() {
        for (int i = 0; i < P2.NUMP; i++) {
            String muestra = P2.MAX + " * n";
            solILS[i] = BLF(i);
            System.out.println(solILS[i].coste + "\t" + solILS[i].eval + "\tn=" + listaElite.count(solILS[i]));
            if (i == 2 && SEED == 333) {
                GraficaS g = new GraficaS(convergencia[i][0], "ILS-BLF", muestra);
                g.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                g.setBounds(200, 350, 800, 500);
                g.setTitle("ILS-BLF - P" + (i + 1) + " - S" + SEED);
                g.setVisible(true);
            }
        }
    }

    public Solucion BLF(int tamP) {
        int[] P = P2.P[tamP];
        int ciu = P[0];
        int cam = P[2];
        int maxeval = P2.MAX * ciu;
        Lista listaPal = P2.listaPal.get(tamP);
        Lista<Integer> conv = new Lista<>();

        int lasteval = -1;
        Solucion elite = new Solucion(new Matriz(1, 1, 0));
        listaElite = new Lista<>();
        int i = 0;
        while (lasteval < maxeval) {
            Solucion inicial;
            if (i == 0) {
                inicial = Solucion.genRandom(cam, listaPal, rand);
            } else {
                inicial = Solucion.genMutacion(cam, elite, rand);
            }
            inicial.eval = lasteval;
            Solucion tmp = BusquedaLocal.BLF(rand, tamP, maxeval, inicial, conv);
            if (elite.coste > tmp.coste) {
                elite = tmp;
            }
            lasteval = tmp.lasteval;
            listaElite.add(tmp);
            i++;
        }

        convergencia[tamP][0] = conv;
        return elite;
    }

    public void ejecutarES() {
        for (int i = 0; i < P2.NUMP; i++) {
            String muestra = (P2.MAX / P2.RESTART / P2.RESTART) + " * n";
            solILS[i] = ES(i);
            System.out.println(solILS[i].coste + "\t" + solILS[i].eval + "\tn=" + listaElite.count(solILS[i]));
            if (i == 2 && SEED == 333) {
                GraficaM g = new GraficaM(convergencia[i], "ILS-ES", muestra);
                g.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                g.setBounds(200, 350, 800, 500);
                g.setTitle("ILS-ES - P" + (i + 1) + " - S" + SEED);
                g.setVisible(true);
            }
        }
    }

    public Solucion ES(int tamP) {
        int[] P = P2.P[tamP];
        int ciu = P[0];
        int cam = P[2];
        int maxeval = P2.MAX * ciu;
        int maxiter = maxeval / P2.RESTART;
        Lista listaPal = P2.listaPal.get(tamP);

        int lasteval = -1;
        Solucion elite = new Solucion(new Matriz(1, 1, 0));
        listaElite = new Lista<>();
        for (int i = 0; i < P2.RESTART; i++) {
            Solucion inicial;
            if (i == 0) {
                inicial = Solucion.genRandom(cam, listaPal, rand);
            } else {
                inicial = Solucion.genMutacion(cam, elite, rand);
            }
            inicial.eval = lasteval;
            Solucion tmp = EnfriamientoSimulado.ES(rand, tamP, maxiter, inicial, convergencia[tamP][i]);
            if (elite.coste > tmp.coste) {
                elite = tmp;
            }
            lasteval = tmp.lasteval;
            listaElite.add(tmp);
        }

        return elite;
    }

    public void ejecutarBT() {
        for (int i = 0; i < P2.NUMP; i++) {
            String muestra = (P2.MAX / P2.RESTART / P2.RESTART) + " * n";
            solILS[i] = BT(i);
            System.out.println(solILS[i].coste + "\t" + solILS[i].eval + "\tn=" + listaElite.count(solILS[i]));
            if (i == 2 && SEED == 333) {
                GraficaM g = new GraficaM(convergencia[i], "ILS-BT", muestra);
                g.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                g.setBounds(200, 350, 800, 500);
                g.setTitle("ILS-BT - P" + (i + 1) + " - S" + SEED);
                g.setVisible(true);
            }
        }
    }

    public Solucion BT(int tamP) {
        int[] P = P2.P[tamP];
        int ciu = P[0];
        int cam = P[2];
        int maxeval = P2.MAX * ciu;
        int maxiter = maxeval / P2.RESTART;
        Lista listaPal = P2.listaPal.get(tamP);

        int lasteval = -1;
        double tenencia = 4.0;
        Solucion elite = new Solucion(new Matriz(1, 1, 0));
        listaElite = new Lista<>();
        for (int i = 0; i < P2.RESTART; i++) {
            if (i > 0) {
                if (rand.nextBoolean()) {
                    tenencia = Math.round(tenencia + tenencia * BusquedaTaboo.KSIZE);
                } else {
                    tenencia = Math.max(Math.round(tenencia - tenencia * BusquedaTaboo.KSIZE), 2.0);
                }
            }
            Solucion inicial;
            if (i == 0) {
                inicial = Solucion.genRandom(cam, listaPal, rand);
            } else {
                inicial = Solucion.genMutacion(cam, elite, rand);
            }
            inicial.eval = lasteval;
            Solucion tmp = BusquedaTaboo.BT(rand, tamP, maxiter, inicial, inicial, convergencia[tamP][i], tenencia);
            if (elite.coste > tmp.coste) {
                elite = tmp;
            }
            lasteval = tmp.lasteval;
            listaElite.add(tmp);
        }

        return elite;
    }

}
