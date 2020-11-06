package main.midlab2.group4.lab.util;

public interface Queue<E> {
    boolean isEmpty();

    void enqueue(E data);

    E dequeue();

    E peek();
}
