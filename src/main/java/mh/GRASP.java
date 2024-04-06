package mh;

import java.util.Random;
import static javax.swing.WindowConstants.*;

/**
 *
 * @author diego
 */
public class GRASP {

    public static final int MAX = 5000;
    public final int SEED;
    public Random rand;
    public Solucion[] solGP;
    public Lista[] convergencia;

    public static final int RESTART = 10;
    public static final int VECIN = 100;

    public GRASP(int a) {
        SEED = a;
        rand = new Random(SEED);
        solGP = new Solucion[P2.NUMP];
        convergencia = new Lista[P2.NUMP];
        for (int i = 0; i < P2.NUMP; i++) {
            convergencia[i] = new Lista<Integer>();
        }
    }

    public void ejecutarGP() {
        for (int i = 0; i < P2.NUMP; i++) {
            solGP[i] = GP(i);
            System.out.println(solGP[i].coste + "\t" + solGP[i].eval);
            if (i == 2 && SEED == 333) {
                Grafica g = new Grafica(convergencia[i], "GRASP");
                g.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                g.setBounds(200, 350, 800, 400);
                g.setTitle("GRASP - P" + (i + 1) + " - S" + SEED);
                g.setVisible(true);
            }
        }
    }

    public Solucion GP(int tamP) {
        int[] P = P2.P[tamP];
        int ciu = P[0];
        int cam = P[2];
        int eval = 0;
        int maxeval = MAX * ciu;
        int iter = 0;
        int maxiter = maxeval / RESTART;
        int reini = 0;
        int vecindario = 0;
        Matriz listaDist = P2.listaDist.get(tamP);

//        Solucion inicial = LRCCamiones(tamP);
        Solucion inicial = LRCPalets(tamP);
        inicial.coste = Solucion.funCoste(inicial, listaDist);
        eval++;
        inicial.eval = eval;
        Solucion candidata, mejor;
        Solucion elite = new Solucion(new Matriz(1, 1, 0));
        convergencia[tamP].add(inicial.coste);

        while (eval < maxeval && reini < RESTART) {
            if (reini > 0) {
//                inicial = LRCCamiones(tamP);
                inicial = LRCPalets(tamP);
                inicial.coste = Solucion.funCoste(inicial, listaDist);
                eval++;
                inicial.eval = eval;
                convergencia[tamP].add(inicial.coste);
                if (elite.coste > inicial.coste) {
                    elite = inicial;
                }
            }

            mejor = inicial;
            iter = 0;
            while (iter < maxiter) {
                vecindario = 0;
                while (vecindario < VECIN) {
                    candidata = Solucion.gen4optAlt(cam, mejor, rand);
                    candidata.coste = Solucion.funCoste(candidata, listaDist);
                    eval++;
                    candidata.eval = eval;
                    if (candidata.eval % 5000 == 0) {
                        convergencia[tamP].add(candidata.coste);
                    }
                    iter++;
                    vecindario++;
                    if (mejor.coste > candidata.coste) {
                        mejor = candidata;
                    }
                }
                if (elite.coste > mejor.coste) {
                    elite = mejor;
                }
            }
            reini++;
        }

        return elite;
    }

    public Solucion LRCCamiones(int tamP) {
        int[] P = P2.P[tamP];
        int cam = P[2];
        Lista<Integer> listaPal = P2.listaPal.get(tamP);
        Matriz listaDist = P2.listaDist.get(tamP);

        int[] ultimopal = new int[cam];
        int[] palxcam = new int[cam];
        for (int i = 0; i < cam; i++) {
            ultimopal[i] = 1;
            palxcam[i] = 0;
        }

        Matriz matriz = new Matriz(cam, P2.MAXPAL, -1);

        for (int i = 0; i < listaPal.size(); i++) {
            int palet = listaPal.get(i);
            int ciupal = palet - 1;
            int[] distcalc = new int[cam];
            for (int j = 0; j < cam; j++) {
                int ciucam = ultimopal[j] - 1;
                distcalc[j] = listaDist.m[ciucam][ciupal];
            }

            Lista<Candidato> LRC = new Lista();
            for (int j = 0; j < cam; j++) {
                if (palxcam[j] < P2.MAXPAL) {
                    LRC.add(new Candidato(j, distcalc[j]));
                }
            }
            Candidato.sort(LRC);

            int limite = (int) (0.5 * cam);
            Candidato elegido = null;
            while (elegido == null) {
                int pos = rand.nextInt(limite);
                if (pos < LRC.size()) {
                    elegido = LRC.get(pos);
                }
            }

            matriz.m[elegido.id][palxcam[elegido.id]] = palet;
            ultimopal[elegido.id] = palet;
            palxcam[elegido.id]++;
//            System.out.println("elegido=" + elegido.id);
//            System.out.println(matriz);
        }

        Solucion s = new Solucion(matriz);
        return s;
    }

    public Solucion LRCPalets(int tamP) {
        int[] P = P2.P[tamP];
        int cam = P[2];
        Lista<Integer> listaP = P2.listaPal.get(tamP);
        Lista<Candidato> listaPal = new Lista<>();
        for (int i = 0; i < listaP.size(); i++) {
            listaPal.add(new Candidato(listaP.get(i), -1));
        }
        Matriz listaDist = P2.listaDist.get(tamP);

        int[] ultimopal = new int[cam];
        for (int i = 0; i < cam; i++) {
            ultimopal[i] = 1;
        }

        Matriz matriz = new Matriz(cam, P2.MAXPAL, -1);

        for (int i = 0; i < P2.MAXPAL; i++) {
            for (int j = 0; j < cam; j++) {
                int ciucam = ultimopal[j] - 1;
                Lista<Candidato> LRC = new Lista<>();
                for (int k = 0; k < listaPal.size(); k++) {
                    int id = listaPal.get(k).id;
                    int ciupal = id - 1;
                    int coste = listaDist.m[ciucam][ciupal];
                    Candidato tmp = new Candidato(id, coste);
                    LRC.add(tmp);
                }
                Candidato.sort(LRC);

                int limite = (int) (0.1 * listaPal.size());
                Candidato elegido = null;
                while (elegido == null) {
                    int pos = -1;
                    if (limite <= 0) {
                        pos = 0;
                    } else {
                        pos = rand.nextInt(limite);
                    }
                    if (pos < LRC.size()) {
                        elegido = LRC.get(pos);
                    }
                }
                listaPal.remove(elegido);

                matriz.m[j][i] = elegido.id;
                ultimopal[j] = elegido.id;
//                System.out.println("elegido=" + elegido.id);
//                System.out.println(matriz);
            }
        }

        Solucion s = new Solucion(matriz);
        return s;
    }
}
