package lab.datastructure;

import java.util.ArrayList;

public class MinPriorityQueue<T extends Comparable<T>> {

    MyGrowingArrayList<T> heap;

    MinPriorityQueue(){
        heap = new MyGrowingArrayList<>();
    }

    public boolean isEmpty(){
        return heap.size()==0;
    }

    public int getSize() {
        return heap.size();
    }

    public void insert(T data) {
        heap.insert(data);
        moveUp();
    }
    public void moveUp(){
        int i = heap.size()-1;
        while (i>0){
            int parentIndex = parent(i);
            T data = heap.get(i);
            T parent = heap.get(parentIndex);
            int key = data.compareTo(parent);
            if (key<0){
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
                int key = heap.get(rightIndex).compareTo(heap.get(max));
                if (key<0){
                    max++;
                }
            }
            int key = heap.get(i).compareTo(heap.get(max));
            if (key>0){;
                swap(heap, i,max);
                i = max;
                leftIndex = getLeftIndex(i);
            }else {
                break;
            }
        }
    }

    public T peek(){
        if (isEmpty()) return null;
        return heap.get(0);
    }

    public T extractMin(){
        if (heap.size()==0){
            return null;
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
    private void swap(MyGrowingArrayList<T> arr, int a, int b){
        T temp = arr.get(a);
        arr.set(a, arr.get(b));
        arr.set(b, temp);
    }

    private int getLeftIndex(int i){
        return 2*i + 1;
    }

    private int getRightIndex(int i){
        return 2*i + 2;
    }

    private int parent(int i){
        return (i-1)/2;
    }


    //A testing program
    public static void main (String [] args)  {
        MinPriorityQueue<String> pq = new MinPriorityQueue<String>();
        pq.insert("cat");
        pq.insert("dog");
        pq.insert("bee");
        System.out.println(pq.toString());
        System.out.println("Smallest is: " + pq.peek());
        System.out.println("Smallest again: " + pq.extractMin());
        System.out.println("Next smallest is: " + pq.extractMin());
        System.out.println("Is it empty? : " + pq.isEmpty());
        pq.insert("eagle");
        System.out.println("Next smallest is: " + pq.extractMin());
        System.out.println("Next smallest is: " + pq.extractMin());
        System.out.println("Is it empty? : " + pq.isEmpty());
        System.out.println("Min of empty queue: " + pq.peek());
        System.out.println("Remove min of empty queue: " + pq.extractMin());
        pq.insert("bear");
        System.out.println("Smallest is: " + pq.peek());
        System.out.println("Smallest again: " + pq.extractMin());
        pq.insert("cat");
        pq.insert("dog");
        pq.insert("sheep");
        pq.insert("cow");
        pq.insert("eagle");
        pq.insert("bee");
        pq.insert("lion");
        pq.insert("tiger");
        pq.insert("zebra");
        pq.insert("ant");
        System.out.println("Bigger example:");
        System.out.println("Smallest is: " + pq.extractMin());
        System.out.println("Next smallest is: " + pq.extractMin());
        System.out.println("Next smallest is: " + pq.extractMin());
        System.out.println("Next smallest is: " + pq.extractMin());
        System.out.println("Next smallest is: " + pq.extractMin());
        System.out.println("Next smallest is: " + pq.extractMin());
        System.out.println("Next smallest is: " + pq.extractMin());
        System.out.println("Next smallest is: " + pq.extractMin());
        System.out.println("Next smallest is: " + pq.extractMin());
        System.out.println("Next smallest is: " + pq.extractMin());
        System.out.println("Next smallest is: " + pq.extractMin());


    }
}
