package lab.datastructure;

import java.util.ArrayList;
import java.util.NoSuchElementException;
public class PriorityQueue<T extends Comparable<T>> {

    ArrayList<T> heap;
    PriorityQueue(){
        heap = new ArrayList<>();
    }

    public boolean isEmpty(){
        return heap.size()==0;
    }

    public int getSize() {
        return heap.size();
    }

    public void insert(T data) {
        heap.add(data);
        moveUp();
    }

    public void moveUp(){
        int i = heap.size()-1;
        while (i>0){
            int parentIndex = parent(i);
            T data = heap.get(i);
            T parent = heap.get(parentIndex);
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
        int leftIndex = getLeftIndex(i); //leftChild index
        while (leftIndex<heap.size()){
            int max = leftIndex;
            int rightIndex =getRightIndex(i);
            if (rightIndex<heap.size()){
                int key=heap.get(rightIndex).compareTo(heap.get(max));
                if (key<0){
                    max++;
                }
            }
            int key = heap.get(i).compareTo(heap.get(max));
            if (key<0){;
                swap(heap, i,max);
                i = max;
                leftIndex = getLeftIndex(i);
            }else {
                break;
            }
        }
    }

    public T peek(){
        if (isEmpty()) throw new NoSuchElementException();
        return heap.get(0);
    }

    public T extractMax(){
        if (heap.size()==0){
            throw new NoSuchElementException();
        }
        if (heap.size()==1)
            return heap.remove(0);

        T temp = heap.get(0);
        //Replace the first element with the last data in the heap
        heap.set(0, heap.remove(heap.size()-1));
        moveDown();
        return temp;
    }

    public String toString(){
        return heap.toString();
    }

    //--------------------------Helper Methods--------------------------------
    private void swap(ArrayList<T> arr, int a, int b){
        T temp = arr.get(a);
        arr.set(a, arr.get(b));
        arr.set(b, temp);
    }

    private static int getLeftIndex(int i){
        return 2*i + 1;
    }

    private static int getRightIndex(int i){
        return 2*i + 2;
    }

    private static int parent(int i){
        return (i-1)/2;
    }
}
