package lab.datastructure;

public interface Stack<T> {
    int size(); /* returns the size of the stack */

    boolean isEmpty(); /* checks if empty */

    T top() throws StackException;

    T pop() throws StackException;

    void push(T item) throws StackException;

    T peek() throws StackException;

    void clear();

    int search(T item); /* return the location of T*/
}