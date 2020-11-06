package main.midlab2.group4.lab.util;

public interface Queue<E> {
    void clear();

    boolean isEmpty();

    void enqueue(E data);

    E dequeue();

    E peek();
}
