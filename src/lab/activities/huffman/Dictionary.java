package lab.activities.huffman;

import lab.util.ArrayList;
import lab.util.List;

import java.util.NoSuchElementException;

public class Dictionary<K, V>{

    List<Node<K, V>> elements;

    private static class Node<K, V> {
        K key;
        V val;

        Node(K k, V v) {
            key = k;
            val = v;
        }

        public String toString() {
            return key + ":" + val;
        }
    }

    Dictionary() {
        elements = new ArrayList<>();
    }

    public void put(K k, V v) {
        final Node<K,V> temp = get(elements, k);
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

