package mh;

import mh.tipos.*;
import mh.algoritmos.*;
import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class P2 {

    public static final int MAX = 5000;
    public static final int MS = MAX;
    public static final int MM = MAX * 2;
    public static final int RESTART = 10;
    public static final int VECIN = 100;
    public static final int MAXPAL = 14;
    public static final int NUMP = 3;
    public static final int[][] P = {{25, 84, 6}, {38, 126, 9}, {50, 168, 12}};
    public static final int[] SEED = {111, 222, 333, 123, 321};
    public static ArrayList<Matriz> listaDist;
    public static ArrayList<Lista> listaPal;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println("RandomTest");
        System.out.println("---------------------");
        RandomTest.randomTest();
        System.out.println("---------------------\n");

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

        System.out.println("\nBV");
        BusquedaVoraz bv = new BusquedaVoraz();
        System.out.println("---------------------");
        bv.ejecutarBV();
        System.out.println("---------------------");

        System.out.println("\nBA");
        BusquedaAleatoria[] ba = new BusquedaAleatoria[SEED.length];
        System.out.println("---------------------");
        for (int i = 0; i < SEED.length; i++) {
            ba[i] = new BusquedaAleatoria(SEED[i]);
            ba[i].ejecutarBA();
            System.out.println("---------------------");
        }

        System.out.println("\nBL");
        BusquedaLocal[] bl = new BusquedaLocal[SEED.length];
        System.out.println("---------------------");
        for (int i = 0; i < SEED.length; i++) {
            bl[i] = new BusquedaLocal(SEED[i]);
            bl[i].ejecutarBL();
            System.out.println("---------------------");
        }

        System.out.println("\nES");
        EnfriamientoSimulado[] es = new EnfriamientoSimulado[SEED.length];
        System.out.println("---------------------");
        for (int i = 0; i < SEED.length; i++) {
            es[i] = new EnfriamientoSimulado(SEED[i]);
            es[i].ejecutarES();
            System.out.println("---------------------");
        }

        System.out.println("\nBT");
        BusquedaTaboo[] bt = new BusquedaTaboo[SEED.length];
        System.out.println("---------------------");
        for (int i = 0; i < SEED.length; i++) {
            bt[i] = new BusquedaTaboo(SEED[i]);
            bt[i].ejecutarBT();
            System.out.println("---------------------");
        }

        System.out.println("\nGRASP-BL");
        GRASP[] gbl = new GRASP[SEED.length];
        System.out.println("---------------------");
        for (int i = 0; i < SEED.length; i++) {
            gbl[i] = new GRASP(SEED[i]);
            gbl[i].ejecutarBL();
            System.out.println("---------------------");
        }

        System.out.println("\nGRASP-ES");
        GRASP[] ges = new GRASP[SEED.length];
        System.out.println("---------------------");
        for (int i = 0; i < SEED.length; i++) {
            ges[i] = new GRASP(SEED[i]);
            ges[i].ejecutarES();
            System.out.println("---------------------");
        }

        System.out.println("\nGRASP-BT");
        GRASP[] gbt = new GRASP[SEED.length];
        System.out.println("---------------------");
        for (int i = 0; i < SEED.length; i++) {
            gbt[i] = new GRASP(SEED[i]);
            gbt[i].ejecutarBT();
            System.out.println("---------------------");
        }

        System.out.println("\nILS-BL");
        ILS[] sbl = new ILS[SEED.length];
        System.out.println("---------------------");
        for (int i = 0; i < SEED.length; i++) {
            sbl[i] = new ILS(SEED[i]);
            sbl[i].ejecutarBL();
            System.out.println("---------------------");
        }

        System.out.println("\nILS-ES");
        ILS[] ses = new ILS[SEED.length];
        System.out.println("---------------------");
        for (int i = 0; i < SEED.length; i++) {
            ses[i] = new ILS(SEED[i]);
            ses[i].ejecutarES();
            System.out.println("---------------------");
        }

        System.out.println("\nILS-BT");
        ILS[] sbt = new ILS[SEED.length];
        System.out.println("---------------------");
        for (int i = 0; i < SEED.length; i++) {
            sbt[i] = new ILS(SEED[i]);
            sbt[i].ejecutarBT();
            System.out.println("---------------------");
        }

        ArrayList<Object> resultados = new ArrayList<>();
        resultados.add(bv);
        resultados.add(ba);
        resultados.add(bl);
        resultados.add(es);
        resultados.add(bt);
        resultados.add(gbl);
        resultados.add(ges);
        resultados.add(gbt);
        resultados.add(sbl);
        resultados.add(ses);
        resultados.add(sbt);

        Parser.escribir("RESULTADOS.txt", resultados);
    }
}
