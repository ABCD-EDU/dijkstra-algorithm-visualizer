package main.finals.grp2.util;

import java.util.NoSuchElementException;

public class Dictionary<K, V> {

    List<Node<K, V>> elements;

    public static class Node<K, V> {
        public K key;
        public V val;

        public Node(K k, V v) {
            key = k;
            val = v;
        }

        public String toString() {
            return String.format("%s%s%s%s%n", "key", key, "val", val);
        }
    }

    public Dictionary() {
        elements = new ArrayList<>();
    }

    public int size() {
        return elements.getSize();
    }

    public void put(K k, V v) {
        final Node<K, V> temp = get(elements, k);
        if (temp != null)
            temp.val = v;
        else {
            elements.insert(new Node<>(k, v));
        }
    }

    public V get(K k) {
        Node<K, V> toReturn = get(elements, k);
        if (toReturn == null) throw new NoSuchElementException();

        return toReturn.val;
    }

    public Node<K,V> getNode(K k) {
        Node<K, V> toReturn = get(elements, k);
        if (toReturn == null) throw new NoSuchElementException();

        return toReturn;
    }

    public Node<K, V> getAt(int pos) {
        return elements.getElement(pos);
    }

    private Node<K, V> get(List<Node<K, V>> list, K k) {
        for (int i = 0; i < list.getSize(); i++) {
            Node<K, V> node = list.getElement(i);
            if (node.key.equals(k)) {
                return node;
            }
        }
        return null;
    }

    public boolean contains(K k) {
        for (int i = 0; i < elements.getSize(); i++) {
            Node<K, V> curr = elements.getElement(i);
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

