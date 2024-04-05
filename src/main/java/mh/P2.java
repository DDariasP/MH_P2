package mh;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class P2 {

    public static final int MAXPAL = 14;
    public static final int NUMP = 3;
    public static final int[][] P = {{25, 84, 6}, {38, 126, 9}, {50, 168, 12}};
    public static final int[] SEED = {111, 222, 333, 123, 321};
    public static ArrayList<Matriz> listaDist;
    public static ArrayList<ListaN> listaPal;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        System.out.println("RandomTest");
//        System.out.println("---------------------");
//        RandomTest.randomTest();
//        System.out.println("---------------------\n");

        listaDist = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int ciu = P[i][0];
            listaDist.add(Parser.leerDist(ciu, "matriz_distancias_" + ciu + ".txt"));
        }

        listaPal = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int pal = P[i][1];
            listaPal.add(Parser.leerPal("destinos_palets_" + pal + ".txt"));
        }

//        System.out.println("BA");
//        BusquedaAleatoria[] ba = new BusquedaAleatoria[SEED.length];
//        System.out.println("---------------------");
//        for (int i = 0; i < SEED.length; i++) {
//            ba[i] = new BusquedaAleatoria(SEED[i]);
//            ba[i].ejecutarBA();
//            System.out.println("---------------------");
//        }
//        System.out.println("");
//
//        System.out.println("BL");
//        BusquedaLocal[] bl = new BusquedaLocal[SEED.length];
//        System.out.println("---------------------");
//        for (int i = 0; i < SEED.length; i++) {
//            bl[i] = new BusquedaLocal(SEED[i]);
//            bl[i].ejecutarBL();
//            System.out.println("---------------------");
//        }
//        System.out.println("");
//
//        System.out.println("ES");
//        EnfriamientoSimulado[] es = new EnfriamientoSimulado[SEED.length];
//        System.out.println("---------------------");
//        for (int i = 0; i < SEED.length; i++) {
//            es[i] = new EnfriamientoSimulado(SEED[i]);
//            es[i].ejecutarES();
//            System.out.println("---------------------");
//        }
//        System.out.println("");
//
//        System.out.println("BT");
//        BusquedaTaboo[] bt = new BusquedaTaboo[SEED.length];
//        System.out.println("---------------------");
//        for (int i = 0; i < SEED.length; i++) {
//            bt[i] = new BusquedaTaboo(SEED[i]);
//            bt[i].ejecutarBT();
//            System.out.println("---------------------");
//        }
//        System.out.println("");
//
//        System.out.println("BV");
//        BusquedaVoraz bv = new BusquedaVoraz();
//        System.out.println("---------------------");
//        bv.ejecutarBV();
//        System.out.println("---------------------");
//        System.out.println("");
//
//        ArrayList<Object> resultados = new ArrayList<>();
//        resultados.add(ba);
//        resultados.add(bl);
//        resultados.add(es);
//        resultados.add(bt);
//        resultados.add(bv);
//
//        Parser.escribir("RESULTADOS.txt", resultados);
    }
}
