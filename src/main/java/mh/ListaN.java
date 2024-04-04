package mh;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class ListaN {

    public ArrayList<Integer> lista;

    public ListaN() {
        lista = new ArrayList<>();
    }

    public void add(int obj) {
        lista.add(obj);
    }

    public void remove(int index) {
        lista.remove(index);
    }

    public int get(int index) {
        return lista.get(index);
    }

    public boolean contains(int obj) {
        return lista.contains(obj);
    }

    public int size() {
        return lista.size();
    }

}
