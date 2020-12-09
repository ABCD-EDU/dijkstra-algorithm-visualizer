package main.finals.grp2.util;

import main.finals.grp2.util.ArrayList;
import main.finals.grp2.util.Queue;

import java.util.NoSuchElementException;

public class PriorityQueue<T extends Comparable<T>>
        implements Queue<T> {

    ArrayList<T> heap;

    public PriorityQueue () {
        heap = new ArrayList<>();
    }

    public boolean isEmpty() {
        return heap.getSize() == 0;
    }

    public int getSize() {
        return heap.getSize();
    }

    public void enqueue(T data) {
        heap.insert(data);
        moveUp();
    }

    public T peek() {
        if (isEmpty()) throw new NoSuchElementException();
        return heap.getElement(0);
    }

    public T dequeue() {
        if (heap.getSize() == 0) {
            throw new NoSuchElementException();
        }
        if (heap.getSize() == 1)
            return heap.remove(0);

        T temp = heap.getElement(0);
        heap.set(0, heap.remove(heap.getSize() - 1));
        moveDown();
        return temp;
    }

    public void remove(T toRemove){
        heap.delete(toRemove);
        moveUp();
    }

    public String toString() {
        return heap.toString();
    }

    //--------------------------Helper Methods--------------------------------
    private void swap(ArrayList<T> arr, int a, int b) {
        T temp = arr.getElement(a);
        arr.set(a, arr.getElement(b));
        arr.set(b, temp);
    }

    private int getLeftIndex(int i) {
        return 2 * i + 1;
    }

    private int getRightIndex(int i) {
        return 2 * i + 2;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }


    private void moveUp() {
        int i = heap.getSize() - 1;
        while (i > 0) {
            int parentIndex = parent(i);
            T data = heap.getElement(i);
            T parent = heap.getElement(parentIndex);
            int key = data.compareTo(parent);
            if (key < 0) {
                swap(heap, i, parentIndex);
                i = parentIndex;
            } else {
                break;
            }
        }
    }

    private void moveDown() {
        int i = 0;
        int leftIndex = getLeftIndex(i);
        while (leftIndex < heap.getSize()) {
            int max = leftIndex;
            int rightIndex = getRightIndex(i);
            if (rightIndex < heap.getSize()) {
                int key = heap.getElement(rightIndex).compareTo(heap.getElement(max));
                if (key < 0) {
                    max++;
                }
            }
            int key = heap.getElement(i).compareTo(heap.getElement(max));
            if (key > 0) {
                swap(heap, i, max);
                i = max;
                leftIndex = getLeftIndex(i);
            } else {
                break;
            }
        }
    }
}
