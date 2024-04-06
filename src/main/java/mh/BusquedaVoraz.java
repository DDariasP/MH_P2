package mh;

/**
 *
 * @author diego
 */
public class BusquedaVoraz {

    public Solucion[] solBV;

    public BusquedaVoraz() {
        solBV = new Solucion[P2.NUMP];
    }

    public void ejecutarBV() {
        for (int i = 0; i < P2.NUMP; i++) {
            solBV[i] = BV(i);
            System.out.println(solBV[i].coste);
            System.out.println(solBV[i].m);
        }
    }

    public Solucion BV(int tamP) {
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
            int distmin = Integer.MAX_VALUE;
            int elegido = -1;
            for (int j = 0; j < cam; j++) {
                if (palxcam[j] < P2.MAXPAL && distmin > distcalc[j]) {
                    distmin = distcalc[j];
                    elegido = j;
                }
            }
            matriz.m[elegido][palxcam[elegido]] = palet;
            ultimopal[elegido] = palet;
            palxcam[elegido]++;
        }

        Solucion s = new Solucion(matriz);
        s.coste = Solucion.funCoste(s, listaDist);
        return s;
    }

}
