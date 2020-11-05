package main.midlab2.group4.lab.util;

public class SinglyLinkedList<T> implements Stack<T> {
    private Node<T> top;
    private int numElements = 0;

    public static class Node<T> {
        private T info;
        private Node<T> link;

        public Node() {
        }

        public Node(T info, Node<T> link) {
            this.info = info;
            this.link = link;
        }

        public void setInfo(T info) {
            this.info = info;
        }

        public void setLink(Node<T> link) {
            this.link = link;
        }

        public T getInfo() {
            return info;
        }

        public Node<T> getLink() {
            return link;
        }
    }

    public int size() {
        return numElements;
    }

    public boolean isEmpty() {
        return (top == null);
    }

    public T top() throws StackException {
        if (isEmpty())
            throw new StackException("Stack is empty.");
        return top.info;
    }

    public T pop() throws StackException {
        if (isEmpty())
            throw new StackException("Stack underflow.");
        final Node<T> oldNode = top;
        final T data = oldNode.info;
        top = oldNode.link;
        oldNode.info = null;
        oldNode.link = null;
        numElements--;
        return data;
    }

    public void push(T item) {
        Node<T> newNode = new Node<>();
        newNode.setInfo(item);
        newNode.setLink(top);
        top = newNode;
        numElements++;
    }

    @Override
    public T peek() throws StackException {
        return top.info;
    }

    @Override
    public void clear() {
        if (isEmpty()) throw new StackException("Stack is empty");

        Node<T> curr = top;
        while (curr != null) {
            final Node<T> old = curr;
            curr = curr.link;
            old.info = null;
            old.link = null;
        }
        numElements = 0;
    }

    @SuppressWarnings("unchecked")
    public String toString() {
        if (this.numElements == 0) return "";
        if (this.numElements == 1) return top.info.toString();

        T[] symbolsArray = (T[]) new Object[numElements];
        StringBuilder toReturn = new StringBuilder();

        // input values in array
        int i = 0;
        for (Node<T> s = top; s != null; s = s.link, i++)
            symbolsArray[i] = s.info;
        // append to string in reverse order of array
        for (int j = symbolsArray.length - 1; j > 0; j--)
            toReturn.append(symbolsArray[j].toString()).append(", ");
        toReturn.append(symbolsArray[0]); // to prevent tailing comma

        return toReturn.toString();
    }
}
