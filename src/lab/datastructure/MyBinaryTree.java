package lab.datastructure;

import java.util.NoSuchElementException;

public class MyBinaryTree<E extends Comparable<E>> {

    Node<E> root;

    private static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        Node(E data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    public MyBinaryTree() {
        root = null;
    }

    public E get(E data) {
        return get(root, data).data;
    }

    public boolean find(E data) {
        return search(this.root, data);
    }

    public void put(E data) {
        this.root = put(this.root, data);
    }

    public void remove(E data) {
        this.root = delete(this.root, data);
    }

    private Node<E> get(Node<E> current, E data) {
        if (root == null) throw new IllegalStateException();

        int key = data.compareTo(current.data);
        if (key < 0)
            return get(current.left, data);
        else if (key > 0)
            return get(current.right, data);
        else
            return current;
    }

    private Node<E> put(Node<E> node, E data) {
        if (node == null) {
            return new Node<>(data);
        } else if (node.data.compareTo(data) > 0) {
            node.left = put(node.left, data);
        } else {
            node.right = put(node.right, data);
        }
        return node;
    }

    private boolean search(Node<E> node, E data) {
        if (node == null) {
            return false;
        } else if (node.data == data) {
            return true;
        } else if (node.data.compareTo(data) < 0) {
            return search(node.left, data);
        } else {
            return search(node.right, data);
        }
    }

    private Node<E> delete(Node<E> node, E data) {
        if (node == null) {
            throw new NoSuchElementException();
        } else if (node.data.compareTo(data) > 0) { // if the current node is greater than the data go left
            node.left = delete(node.left, data);
        } else if (node.data.compareTo(data) < 0) { // if the current node is less than the data go right
            node.right = delete(node.right, data);
        } else {
            if (node.right == null && node.left == null) { // If it is leaf node
                node = null;
            } else if (node.left == null) { // If only right node is present remove right
                Node<E> newNode = node.right;
                node.right = null;
                node = newNode;
            } else if (node.right == null) { // Only left node is present remove left
                Node<E> newNode = node.left;
                node.left = null;
                node = newNode;
            } else { // both child are present
                Node<E> newNode = node.right;
                while (newNode.left != null) { // finds the min node
                    newNode = newNode.left;
                }
                node.data = newNode.data;
                node.right = delete(node.right, newNode.data);
            }
        }
        return node;
    }
}