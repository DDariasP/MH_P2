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

    public static ListaN leerPal(String filename) {
        ListaN listaPal = new ListaN();
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

            writer.write("BA - n*" + BusquedaAleatoria.MAX);
            writer.write("\n---------------------");
            BusquedaAleatoria[] ba = (BusquedaAleatoria[]) lista.get(0);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + ba[i].solBA[j].coste + "\t" + ba[i].solBA[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nBL - n*" + BusquedaLocal.MAX);
            writer.write("\n---------------------");
            BusquedaLocal[] bl = (BusquedaLocal[]) lista.get(1);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + bl[i].solBL[j].coste + "\t" + bl[i].solBL[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nES - n*" + EnfriamientoSimulado.MAX);
            writer.write("\n---------------------");
            EnfriamientoSimulado[] es = (EnfriamientoSimulado[]) lista.get(2);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + es[i].solES[j].coste + "\t" + es[i].solES[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nBT - n*" + BusquedaTaboo.MAX);
            writer.write("\n---------------------");
            BusquedaTaboo[] bt = (BusquedaTaboo[]) lista.get(3);
            for (int i = 0; i < P2.SEED.length; i++) {
                for (int j = 0; j < P2.NUMP; j++) {
                    writer.write("\n" + bt[i].solBT[j].coste + "\t" + bt[i].solBT[j].eval);
                }
                writer.write("\n---------------------");
            }
            writer.write("\n---------------------");

            writer.write("\nBV");
            writer.write("\n---------------------");
            BusquedaVoraz bv = (BusquedaVoraz) lista.get(4);
            for (int i = 0; i < P2.NUMP; i++) {
                writer.write("\n" + bv.solBV[i].coste);
                writer.write("\n" + bv.solBV[i].m.toString());
            }
            writer.write("\n---------------------");

            writer.close();
        } catch (IOException e) {
            System.out.println("Error en File.");
        }
    }

}
