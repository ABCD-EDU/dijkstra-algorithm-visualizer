package main.midlab2.group4.lab.activities.huffman;

import main.midlab2.group4.lab.util.ArrayList;
import main.midlab2.group4.lab.util.List;

// TODO: RENAME
public class PairList<K, V> {

    List<PairList.Node<K, V>> elements;

    public static class Node<K, V> {
        K x;
        V y;

        Node(K k, V v) {
            x = k;
            y = v;
        }

        public String toString() {
            return String.format("%-30s%-30s%n", x, y);
        }
    }

    PairList() {
        elements = new ArrayList<>();
    }

    public int size() {
        return elements.getSize();
    }

    public void put(K k, V v) {
        elements.insert(new PairList.Node<>(k, v));
    }
//
//    public V get(K k) {
//        Dictionary.Node<K, V> toReturn = get(elements, k);
//        if (toReturn == null) throw new NoSuchElementException();
//
//        return toReturn.val;
//    }

    public PairList.Node<K, V> getAt(int pos) {
        return elements.getElement(pos);
    }

    private Dictionary.Node<K, V> get(List<Dictionary.Node<K, V>> list, K k) {
        for (int i = 0; i < list.getSize(); i++) {
            Dictionary.Node<K, V> node = list.getElement(i);
            if (node.key.equals(k)) {
                return node;
            }
        }
        return null;
    }

    public boolean contains(K k) {
        for (int i = 0; i < elements.getSize(); i++) {
            PairList.Node<K, V> curr = elements.getElement(i);
            if (curr.x.equals(k))
                return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < elements.getSize(); i++) {
            sb.append(elements.getElement(i)).append(" ");
        }

        return sb.toString();
    }

}
