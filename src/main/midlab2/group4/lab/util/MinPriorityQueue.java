package main.midlab2.group4.lab.util;

public class MinPriorityQueue<T extends Comparable<T>> implements Queue<T> {

    ArrayList<T> heap;

    public MinPriorityQueue() {
        heap = new ArrayList<>();
    }

    @Override
    public void clear() {
        // TODO: not the correct way. find a fix later
        heap = null;
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
        if (isEmpty()) return null;
        return heap.getElement(0);
    }

    public T dequeue() {
        if (heap.getSize() == 0) {
            return null;
        }
        if (heap.getSize() == 1)
            return heap.remove(0);

        T temp = heap.getElement(0);
        //Replace the first element with the last data in the heap
        heap.set(0, heap.remove(heap.getSize() - 1));
        moveDown();
        return temp;
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

    public void moveUp() {
        int i = heap.getSize() - 1;
        while (i > 0) {
            int parentIndex = parent(i);
            T data = heap.getElement(i);
            T parent = heap.getElement(parentIndex);
            int key = data.compareTo(parent);
            if (key < 0) {
                //swap
                swap(heap, i, parentIndex);
                //move up one level
                i = parentIndex;
            } else {
                break;
            }
        }
    }

    public void moveDown() {
        int i = 0;
        int leftIndex = getLeftIndex(i); //leftChild index
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
                ;
                swap(heap, i, max);
                i = max;
                leftIndex = getLeftIndex(i);
            } else {
                break;
            }
        }
    }
}
