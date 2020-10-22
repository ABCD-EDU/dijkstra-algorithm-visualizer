package lab.datastructure;

import java.util.NoSuchElementException;
public class PriorityQueue<T extends Comparable<T>> {

    MyArrayList<T> heap;
    PriorityQueue(){
        heap = new MyArrayList<>();
    }

    public boolean isEmpty(){
        return heap.getSize()==0;
    }

    public int getElementgetSize() {
        return heap.getSize();
    }

    public void insert(T data) {
        heap.insert(data);
        moveUp();
    }

    public void moveUp(){
        int i = heap.getSize()-1;
        while (i>0){
            int parentIndex = parent(i);
            T data = heap.getElement(i);
            T parent = heap.getElement(parentIndex);
            int key = data.compareTo(parent);
            if (key>0 ){
                //swap
                swap(heap, i, parentIndex);
                //move up one level
                i = parentIndex;
            }else {
                break;
            }
        }
    }

    public void moveDown(){
        int i= 0;
        int leftIndex = getElementLeftIndex(i); //leftChild index
        while (leftIndex<heap.getSize()){
            int max = leftIndex;
            int rightIndex =getElementRightIndex(i);
            if (rightIndex<heap.getSize()){
                int key=heap.getElement(rightIndex).compareTo(heap.getElement(max));
                if (key<0){
                    max++;
                }
            }
            int key = heap.getElement(i).compareTo(heap.getElement(max));
            if (key<0){
                swap(heap, i,max);
                i = max;
                leftIndex = getElementLeftIndex(i);
            }else {
                break;
            }
        }
    }

    public T peek(){
        if (isEmpty()) throw new NoSuchElementException();
        return heap.getElement(0);
    }

    public T extractMax(){
        if (heap.getSize()==0){
            throw new NoSuchElementException();
        }
        if (heap.getSize()==1)
            return heap.remove(0);

        T temp = heap.getElement(0);
        //Replace the first element with the last data in the heap
        heap.set(0, heap.remove(heap.getSize()-1));
        moveDown();
        return temp;
    }

    public String toString(){
        return heap.toString();
    }

    //--------------------------Helper Methods--------------------------------
    private void swap(MyArrayList<T> arr, int a, int b){
        T temp = arr.getElement(a);
        arr.set(a, arr.getElement(b));
        arr.set(b, temp);
    }

    private static int getElementLeftIndex(int i){
        return 2*i + 1;
    }

    private static int getElementRightIndex(int i){
        return 2*i + 2;
    }

    private static int parent(int i){
        return (i-1)/2;
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.insert(1);
        pq.insert(2);
        pq.insert(3);
        pq.insert(4);
        pq.insert(5);
        pq.insert(6);
        pq.insert(7);
        pq.insert(8);
        System.out.println("Bigger example:");
        System.out.println("Biggest is: " + pq.extractMax());
        System.out.println("Next Biggest is: " + pq.extractMax());
        System.out.println("Next Biggest is: " + pq.extractMax());
        System.out.println("Next Biggest is: " + pq.extractMax());
        System.out.println("Next Biggest is: " + pq.extractMax());
        System.out.println("Next Biggest is: " + pq.extractMax());
        System.out.println("Next Biggest is: " + pq.extractMax());
        System.out.println("Next Biggest is: " + pq.extractMax());
        System.out.println("Next Biggest is: " + pq.extractMax());
    }
}
