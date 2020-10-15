package lab.datastructure;

import java.util.NoSuchElementException;

public class MyLinkedList<E>
        implements Stack<E>, MyList<E>, Queue<E> {

    Node<E> head;
    Node<E> tail;
    int size;

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E item, Node<E> next) {
            this.data = item;
            this.next = next;
            this.prev = prev;
        }
    }

    public MyLinkedList() {}

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void insert(E data) throws ListOverflowException {
        try {
            insertTail(data);
        }catch (OutOfMemoryError e) {
            throw new ListOverflowException("Out of Memory");
        }
    }

    @Override
    public E getElement(E data) throws NoSuchElementException {
        if (findData(data) == null)
            throw new NoSuchElementException("The data does not exist.");
        return findData(data).data;
    }

    @Override
    public boolean delete(E data) {
        final Node<E> node = findData(data);
        if (node != null) {
            deleteNode(node);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean enqueue(E data) {
        insertHead(data);
        return true;
    }

    @Override
    public E dequeue() {
        return attainFirst();
    }

    @Override
    public E firstElement() {
        return head.data;
    }

    @Override
    public E pop() throws StackException {
        if (isEmpty()) throw new StackException("Stack underflow.");
        return attainLast();
    }

    @Override
    public void push(E item) throws StackException {
        insertTail(item);
    }

    @Override
    public E peek() throws StackException {
        return tail.data;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean search(E data) {
        return findData(data) == null;
    }

    public void deleteFirst() {
        if (!isEmpty()) {
            deleteFirstNode();
        }
    }

    public void deleteLast() {
        if (!isEmpty()) {
            deleteEndNode();
        }
    }

    void insertHead(E data) {
        final Node<E> prevHead = head;
        final Node<E> newNode = new Node<>(null , data, prevHead);
        if (!isEmpty()) {
            head = prevHead.prev = newNode;
        }else {
            head = tail = newNode;
        }
        size++;
    }

    void insertTail(E data) {
        final Node<E> prevTail = tail;
        final Node<E> newNode = new Node<>(prevTail, data, null);
        if (!isEmpty()) {
            tail = prevTail.next = newNode;
        }else {
            head = tail = newNode;
        }
        size++;
    }

    public E attainLast() {
        final Node<E> newTail = tail.prev;
        deleteLast();
        return newTail.data;
    }

    public E attainFirst() {
        final Node<E> newHead = head.next;
        deleteFirst();
        return newHead.data;
    }

    Node<E> findData(E node) {
        Node<E> pointer = head;
        while (pointer != null) {
            if (pointer.data.equals(node))
                return pointer;
            pointer = pointer.next;
        }
        return null;
    }

    void deleteNode(Node<E> node) {
        final Node<E> next = node.next;
        final Node<E> prev = node.prev;
        if (next == null) {
            this.deleteEndNode();
        } else if (prev == null) {
            this.deleteFirstNode();
        } else {
            prev.next = node.next;
            next.prev = node.prev;
            node.next = node.prev = null;
            node.data = null;
            size--;
        }
    }

    void deleteFirstNode() {
        final Node<E> newHead = head.next;
        if (!isEmpty()) {
            if (head == tail) {
                head = tail = null;
            }else {
                head.data = null;
                head.next = newHead.prev = null;
                head = newHead;
            }
        }
        size--;
    }

    void deleteEndNode(){
        final Node<E> newTail = tail.prev;
        if (!isEmpty()) {
            if (head == tail) {
                head = tail = null;
            }else {
                tail.data = null;
                tail.prev = newTail.next = null;
                tail = newTail;
            }
        }
        size--;
    }
}
