package mh;

import java.util.Random;
import static javax.swing.WindowConstants.*;

/**
 *
 * @author diego
 */
public class EnfriamientoSimulado {

    public static final int MAX = 5000;
    public final int SEED;
    public Random rand;
    public Solucion[] solES;
    public ListaD[] convergencia;

    public static final double KA = 0.9;
    public static final int VECIN = 100;
    public static final int KI = 50;
    public double T, delta, aceptacion, T0, sigma, lnCiu;

    public EnfriamientoSimulado(int a) {
        SEED = a;
        rand = new Random(SEED);
        solES = new Solucion[P2.NUMP];
        convergencia = new ListaD[P2.NUMP];
        for (int i = 0; i < P2.NUMP; i++) {
            convergencia[i] = new ListaD();
        }
    }

    public void ejecutarES() {
        for (int i = 0; i < P2.NUMP; i++) {
            solES[i] = ES(i);
            System.out.println(solES[i].coste + "\t" + solES[i].eval);
            if (i == 2 && SEED == 333) {
                Grafica g = new Grafica(convergencia[i], "ES");
                g.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                g.setBounds(200, 350, 800, 400);
                g.setTitle("ES - P" + (i + 1) + " - S" + SEED);
                g.setVisible(true);
            }
        }
    }

    public Solucion ES(int tamP) {
        Integer[] P = P2.P.get(tamP);
        int ciu = P[0];
        int cam = P[2];
        ListaN listaPal = P2.listaPal.get(tamP);
        Matriz listaDist = P2.listaDist.get(tamP);
        int filas = listaDist.filas;

        double dividendo = 0.0;
        double divisor = 0.0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j <= i; j++) {
                int d = listaDist.m[i][j];
                dividendo = dividendo + d;
                divisor++;

            }
        }
        double media = dividendo / divisor;

        double sumatorio = 0.0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j <= i; j++) {
                int d = listaDist.m[i][j];
                double resta = d - media;
                sumatorio = sumatorio + Math.pow(resta, 2);

            }
        }
        sigma = Math.sqrt(sumatorio / divisor);
        lnCiu = Math.log(ciu);
        T0 = sigma / lnCiu;

        T = T0;
        int eval = 0;
        int maxeval = MAX * ciu;
        int enfr = 0;
        int maxenfr = KI * ciu;

        Solucion mejor = Solucion.genRandom(cam, listaPal, rand);
        mejor.coste = Solucion.funCoste(mejor, listaDist);
        eval++;
        mejor.eval = eval;
        mejor.T0 = T0;
        mejor.TF = T0;
        mejor.enfr = enfr;
        Double d = Double.valueOf(mejor.coste);
        convergencia[tamP].add(d);

        while (true) {
            int vecindario = 0;
            while (true) {
                Solucion candidata = Solucion.gen2optAlt(cam, mejor, rand);
                candidata.coste = Solucion.funCoste(candidata, listaDist);
                eval++;
                candidata.eval = eval;
                if (candidata.eval % 5000 == 0) {
                    d = Double.valueOf(candidata.coste);
                    convergencia[tamP].add(d);
                }
                candidata.T0 = T0;
                candidata.TF = T;
                candidata.enfr = enfr;
                vecindario++;
                delta = candidata.coste - mejor.coste;
                aceptacion = rand.nextDouble();
                if (delta < 0 || aceptacion < Math.exp(-delta / T)) {
                    mejor = candidata;

                }
                if (vecindario == VECIN - 1) {
                    T = KA * T;
                    enfr++;
                    break;
                }
                if (eval == maxeval - 1) {
                    break;
                }
            }
            if (enfr == maxenfr - 1) {
                break;
            }
            if (eval == maxeval - 1) {
                break;
            }
        }

        return mejor;
    }
}
