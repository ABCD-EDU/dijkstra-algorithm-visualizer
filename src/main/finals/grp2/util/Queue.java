package main.finals.grp2.util;

public interface Queue<E> {
    boolean isEmpty();

    void enqueue(E data);

    E dequeue();

    E peek();
}
