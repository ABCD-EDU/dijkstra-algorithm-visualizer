package lab.datastructure;

/**
 * Only the enqueue, dequeue, firstElement functions were coded in the MyLinkedList.java class due to conflict
 * with the Stack data structure when it comes to naming.
 *
 * {@see MyLinkedList.java}
 * @param <E>
 */
public interface Queue<E> {
    void clear();

    boolean isEmpty();

    boolean enqueue(E data);

    E dequeue();

    E firstElement();
}
