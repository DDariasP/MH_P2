package mh;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class ListaM {

    public ArrayList<Movimiento> lista;

    public ListaM() {
        lista = new ArrayList<>();
    }

    public void add(Movimiento obj) {
        lista.add(obj);
    }

    public void remove(int index) {
        lista.remove(index);
    }

    public Movimiento get(int index) {
        return lista.get(index);
    }

    public boolean contains(Movimiento obj) {
        return lista.contains(obj);
    }

    public int size() {
        return lista.size();
    }

}
