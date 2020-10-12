package lab.datastructure;

public class MyLinkedList<T> implements Stack<T> {
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

    @Override
    public int search(T item) {
        int pos = 0;
        for (Node<T> c = top; c != null; c = c.link) {
            if (c.info.equals(item))
                return pos;
            pos++;
        }
        return -1;
    }

    /**
     * This toString method returns top, top.next, ... , last item
     * basically, 10,9,8,...,0 not 0,1,2,..,10
     *
     * @return string form
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Node<T> s = top; s != null; s = s.link) {
            sb.append(s.info).append(" ");
        }

        return sb.toString();
    }
}