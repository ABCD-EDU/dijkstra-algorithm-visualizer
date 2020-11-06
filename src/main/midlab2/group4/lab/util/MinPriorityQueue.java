package main.midlab2.group4.lab.util;

public class MinPriorityQueue<T extends Comparable<T>> implements Queue<T> {

    private Node<T> root;
    private Node<T> last;
    private int size;

    public static class Node<T> {
        Node<T> parent;
        Node<T> left;
        Node<T> right;
        Node<T> prevLast;
        T data;

        Node(T data) {
            this.data = data;
        }
    }

    public void enqueue(T data) {
        if (root == null) {
            root = new Node<>(data);
            last = root;
        } else if (last.left == null) {
            last.left = new Node<>(data);
            last.left.parent = last;
            moveUp(last.left);
        } else if (last.right == null) {
            last.right = new Node<>(data);
            last.right.parent = last;
            moveUp(last.right);
            Node<T> prevLast = last;
            setLast(last);
            last.prevLast = prevLast;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) return null;
        if (size == 1) {
            T temp = root.data;
            last = null;
            root = null;
            size--;
            return temp;
        }
        T temp = root.data;
        if (size == 2) {
            swapNodeData(root, root.left);
            root.left = null;
            size--;
            return temp;
        }

        if (isLeaf(last)) {
            last = last.prevLast;
            swapNodeData(root, last.right);
            last.right = null;
        } else {
            if (last.right != null) {
                swapNodeData(root, last.right);
                last.right = null;
            } else {
                swapNodeData(root, last.left);
                last.left = null;
            }
        }
        size--;
        moveDown(root);
        return temp;
    }

    @Override
    public T peek() {
        if (size != 0)
            return last.data;
        else
            return null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    void swapNodeData(Node<T> a, Node<T> b) {
        T temp = a.data;
        a.data = b.data;
        b.data = temp;
    }

    void moveUp(Node<T> node) {
        if (node.parent != null) {
            if (node.data.compareTo(node.parent.data) < 0) {
                swapNodeData(node.parent, node);
                moveUp(node.parent);
            }
        }
    }

    void moveDown(Node<T> node) {
        if (node == null || node.left == null) {
            return;
        }

        Node<T> min = node.left;
        if (node.right != null && min.data.compareTo(node.right.data) > 0) {
            min = node.right;

        }
        if (min.data.compareTo(node.data) < 0) {
            swapNodeData(node, min);
            moveDown(min);
        }
    }

    void setLast(Node<T> node) {
        if (node.parent == null) {
            last = node;
            while (last.left != null) {
                last = last.left;
            }
        } else if (node.parent.left == node) {
            last = node.parent.right;
            while (last.left != null) {
                last = last.left;
            }
        } else if (node.parent.right == node) {
            setLast(node.parent);
        }
    }

    boolean isLeaf(Node<T> node) {
        return node.left == null && node.right == null;
    }
}
