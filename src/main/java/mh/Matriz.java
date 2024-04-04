package mh;

/**
 *
 * @author diego
 */
public class Matriz {

    public final int filas, columnas;
    public int[][] m;

    public Matriz(int a, int b, int c) {
        filas = a;
        columnas = b;
        m = new int[a][b];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                m[i][j] = c;
            }
        }
    }

    public Matriz(Matriz copia) {
        filas = copia.filas;
        columnas = copia.columnas;
        m = new int[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                m[i][j] = copia.m[i][j];
            }
        }
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                output = output + m[i][j] + " ";
            }
            output = output + "\n";
        }
        return output;
    }

}
