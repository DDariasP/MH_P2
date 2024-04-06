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
    public Lista[] convergencia;

    public static final double KA = 0.9;
    public static final int VECIN = 100;
    public static final int KI = 50;
    public double T, delta, aceptacion, T0, sigma, lnCiu;

    public EnfriamientoSimulado(int a) {
        SEED = a;
        rand = new Random(SEED);
        solES = new Solucion[P2.NUMP];
        convergencia = new Lista[P2.NUMP];
        for (int i = 0; i < P2.NUMP; i++) {
            convergencia[i] = new Lista<Integer>();
        }
    }

    public void ejecutarES() {
        for (int i = 0; i < P2.NUMP; i++) {
            solES[i] = ES(i);
            System.out.println(solES[i].coste + "\t" + solES[i].eval);
            if (i == 2 && SEED == 333) {
                GraficaS g = new GraficaS(convergencia[i], "ES");
                g.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                g.setBounds(200, 350, 800, 400);
                g.setTitle("ES - P" + (i + 1) + " - S" + SEED);
                g.setVisible(true);
            }
        }
    }

    public Solucion ES(int tamP) {
        int[] P = P2.P[tamP];
        int ciu = P[0];
        int cam = P[2];
        Lista listaPal = P2.listaPal.get(tamP);
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

        Solucion inicial = Solucion.genRandom(cam, listaPal, rand);
        inicial.coste = Solucion.funCoste(inicial, listaDist);
        eval++;
        inicial.eval = eval;
        inicial.T0 = T0;
        inicial.TF = T0;
        inicial.enfr = enfr;
        convergencia[tamP].add(inicial.coste);

        Solucion actual = inicial;
        while (true) {
            int vecindario = 0;
            while (true) {
                Solucion siguiente = Solucion.gen2optAlt(cam, actual, rand);
                siguiente.coste = Solucion.funCoste(siguiente, listaDist);
                eval++;
                siguiente.eval = eval;
                if (siguiente.eval % MAX == 0) {
                    convergencia[tamP].add(siguiente.coste);
                }
                siguiente.T0 = T0;
                siguiente.TF = T;
                siguiente.enfr = enfr;
                vecindario++;
                delta = siguiente.coste - actual.coste;
                aceptacion = rand.nextDouble();
                if (delta < 0 || aceptacion < Math.exp(-delta / T)) {
                    actual = siguiente;

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

        return actual;
    }

    public static Solucion ES(Random rand, int tamP, int maxiter, Solucion inicial, Lista<Integer> convergencia) {
        int[] P = P2.P[tamP];
        int ciu = P[0];
        int cam = P[2];
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
        double sigma = Math.sqrt(sumatorio / divisor);
        double lnCiu = Math.log(ciu);
        double T0 = sigma / lnCiu;

        double T = T0;
        int iter = 0;
        int eval = inicial.eval;
        int enfr = 0;
        int maxenfr = KI * ciu;

        inicial.coste = Solucion.funCoste(inicial, listaDist);
        iter++;
        eval++;
        inicial.eval = eval;
        inicial.T0 = T0;
        inicial.TF = T0;
        inicial.enfr = enfr;
        convergencia.add(inicial.coste);
        int muestra = maxiter / GRASP.RESTART;

        Solucion actual = inicial;
        while (true) {
            int vecindario = 0;
            while (true) {
                Solucion siguiente = Solucion.gen2optAlt(cam, actual, rand);
                siguiente.coste = Solucion.funCoste(siguiente, listaDist);
                iter++;
                eval++;
                siguiente.eval = eval;
                if (siguiente.eval % muestra == 0) {
                    convergencia.add(siguiente.coste);
                }
                siguiente.T0 = T0;
                siguiente.TF = T;
                siguiente.enfr = enfr;
                vecindario++;
                double delta = siguiente.coste - actual.coste;
                double aceptacion = rand.nextDouble();
                if (delta < 0 || aceptacion < Math.exp(-delta / T)) {
                    actual = siguiente;

                }
                if (vecindario == GRASP.VECIN - 1) {
                    T = KA * T;
                    enfr++;
                    break;
                }
                if (iter == maxiter - 1) {
                    break;
                }
            }
            if (enfr == maxenfr - 1) {
                break;
            }
            if (iter == maxiter - 1) {
                break;
            }
        }
        
        actual.lasteval = eval;
        return actual;
    }
}
