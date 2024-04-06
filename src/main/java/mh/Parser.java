package mh;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author diego
 */
public class Parser {

    public static Matriz leerDist(int ciu, String filename) {
        Matriz listaDist = new Matriz(ciu, ciu, -1);
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String line;
            String[] tokens;
            int contador = 0;
            while (contador < ciu) {
                line = scanner.nextLine();
                tokens = line.split("\\s+");
                int[] fila = new int[ciu];
                for (int i = 0; i < ciu; i++) {
                    fila[i] = Integer.parseInt(tokens[i]);
                }
                listaDist.m[contador] = fila;
                contador++;
            }
            scanner.close();
        } catch (IOException ex) {
            System.out.println("Error en File.");
        }
        return listaDist;
    }

    public static Lista<Integer> leerPal(String filename) {
        Lista<Integer> listaPal = new Lista<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                listaPal.add(Integer.valueOf(line));
            }
            scanner.close();
        } catch (IOException ex) {
            System.out.println("Error en File.");
        }
        return listaPal;
    }

    public static void escribir(String filename, ArrayList<Object> lista) {
        try {
            File resultados = new File(filename);
            if (resultados.exists()) {
                resultados.delete();
                System.out.println("\nArchivo " + resultados.getName() + " sobreescrito.\n");
            } else {
                System.out.println("\nArchivo " + resultados.getName() + " creado.\n");
            }
            resultados.createNewFile();
            FileWriter writer = new FileWriter(filename);

            writer.write("BV");
            writer.write("\n---------------------");
            BusquedaVoraz bv = (BusquedaVoraz) lista.get(0);
            for (int i = 0; i < P2.NUMP; i++) {
                writer.write("\n" + bv.solBV[i].coste);
                writer.write("\n" + bv.solBV[i].m.toString());
            }
            writer.write("\n---------------------");

            writer.write("\nBA - n*" + P2.MAX);
            writer.write("\n---------------------");
            BusquedaAleatoria[] ba = (BusquedaAleatoria[]) lista.get(1);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + ba[i].solBA[j].coste + "\t" + ba[i].solBA[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nBL - n*" + P2.MAX);
            writer.write("\n---------------------");
            BusquedaLocal[] bl = (BusquedaLocal[]) lista.get(2);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + bl[i].solBL[j].coste + "\t" + bl[i].solBL[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nES - n*" + P2.MAX);
            writer.write("\n---------------------");
            EnfriamientoSimulado[] es = (EnfriamientoSimulado[]) lista.get(3);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + es[i].solES[j].coste + "\t" + es[i].solES[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nBT - n*" + P2.MAX);
            writer.write("\n---------------------");
            BusquedaTaboo[] bt = (BusquedaTaboo[]) lista.get(4);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + bt[i].solBT[j].coste + "\t" + bt[i].solBT[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nGRASP-BL - n*" + P2.MAX);
            writer.write("\n---------------------");
            GRASP[] gbl = (GRASP[]) lista.get(5);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + gbl[i].solGP[j].coste + "\t" + gbl[i].solGP[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nGRASP-ES - n*" + P2.MAX);
            writer.write("\n---------------------");
            GRASP[] ges = (GRASP[]) lista.get(6);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + ges[i].solGP[j].coste + "\t" + ges[i].solGP[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nGRASP-BT - n*" + P2.MAX);
            writer.write("\n---------------------");
            GRASP[] gbt = (GRASP[]) lista.get(7);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + gbt[i].solGP[j].coste + "\t" + gbt[i].solGP[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nILS-BL - n*" + P2.MAX);
            writer.write("\n---------------------");
            ILS[] sbl = (ILS[]) lista.get(8);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + sbl[i].solILS[j].coste + "\t" + sbl[i].solILS[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nILS-ES - n*" + P2.MAX);
            writer.write("\n---------------------");
            ILS[] ses = (ILS[]) lista.get(9);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + ses[i].solILS[j].coste + "\t" + ses[i].solILS[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nILS-BT - n*" + P2.MAX);
            writer.write("\n---------------------");
            ILS[] sbt = (ILS[]) lista.get(10);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + sbt[i].solILS[j].coste + "\t" + sbt[i].solILS[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.close();
        } catch (IOException e) {
            System.out.println("Error en File.");
        }
    }

}
