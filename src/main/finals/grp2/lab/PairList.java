package main.finals.grp2.lab;

import main.finals.grp2.util.ArrayList;
import main.finals.grp2.util.List;

import java.security.InvalidParameterException;
import java.util.Objects;

public class PairList<K, V> {

    List<Node<K, V>> elements;

    public static class Node<K, V> {
        public K key;
        public V val;

        public Node(K k, V v) {
            key = k;
            val = v;
        }

        public String toString() {
            return String.format("%-30s%-30s%n", key, val);
        }

        @Override
        public boolean equals(Object o) {
            Node<?, ?> node = (Node<?, ?>) o;
            return Objects.equals(key, node.key) &&
                    Objects.equals(val, node.val);
        }

    }

    public PairList() {
        elements = new ArrayList<>();
    }

    public int size() {
        return elements.getSize();
    }

    public void put(K k, V v) {
        elements.insert(new PairList.Node<>(k, v));
    }

    public PairList.Node<K, V> getAt(int pos) {
        return elements.getElement(pos);
    }

    public V get(K key) {
        for (int i = 0; i < elements.getSize(); i++) {
            if (elements.getElement(i).key.equals(key)) {
                return elements.getElement(i).val;
            }
        }
        throw new InvalidParameterException("Key does not exist");
    }

    public boolean contains(K k) {
        for (int i = 0; i < elements.getSize(); i++) {
            PairList.Node<K, V> curr = elements.getElement(i);
            if (curr.key.equals(k))
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