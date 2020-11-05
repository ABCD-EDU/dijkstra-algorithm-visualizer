package main.midlab2.group4.lab.util;

public interface Stack<T> {
    int size();

    boolean isEmpty();

    T pop() throws StackException;

    void push(T item) throws StackException;

    T peek() throws StackException;

    void clear();
}