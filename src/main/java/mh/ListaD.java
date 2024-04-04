package mh;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class ListaD {

    public ArrayList<Double> lista;

    public ListaD() {
        lista = new ArrayList<>();
    }

    public void add(double obj) {
        lista.add(obj);
    }

    public void remove(int index) {
        lista.remove(index);
    }

    public double get(int index) {
        return lista.get(index);
    }

    public boolean contains(double obj) {
        return lista.contains(obj);
    }

    public int size() {
        return lista.size();
    }

}
