package lab.datastructure;
import java.util.NoSuchElementException;
public class MyPriorityQueue<T extends Comparable<T>> {

    MyGrowingArrayList<T> heap;

    MyPriorityQueue(){
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
                int key = heap.get(rightIndex).compareTo(heap.get(max));
                if (key>0){
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

    public static void main(String[] args) {
        MyPriorityQueue<String> pq = new MyPriorityQueue<>();
        pq.insert("teen");
        pq.insert("MILF");
        pq.insert("BBC");
        System.out.println("Biggest is: " + pq.peek());
        System.out.println("Biggest again: " + pq.extractMax());
        System.out.println("Next Biggest is: " + pq.extractMax());
        System.out.println("Is it empty? : " + pq.isEmpty());
        pq.insert("GILF");
        System.out.println("Next Biggest is: " + pq.extractMax());
        System.out.println("Next Biggest is: " + pq.extractMax());
        System.out.println("Is it empty? : " + pq.isEmpty());
        System.out.println("Min of empty queue: " + pq.peek());
        System.out.println("Remove min of empty queue: " + pq.extractMax());
        pq.insert("bear");
        System.out.println("Biggest is: " + pq.peek());
        System.out.println("Biggest again: " + pq.extractMax());

//
//        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();
//        pq.insert(10);
//        pq.insert(12);
//        pq.insert(8);
//        pq.insert(4);
//        pq.insert(2);
//        pq.insert(19);
//        pq.insert(20);
//        pq.insert(6);
//        pq.insert(5);
//        pq.insert(1);
//        System.out.println("Bigger example:");
//        System.out.println("Biggest is: " + pq.extractMax());
//        System.out.println("Next Biggest is: " + pq.extractMax());
//        System.out.println("Next Biggest is: " + pq.extractMax());
//        System.out.println("Next Biggest is: " + pq.extractMax());
//        System.out.println("Next Biggest is: " + pq.extractMax());
//        System.out.println("Next Biggest is: " + pq.extractMax());
//        System.out.println("Next Biggest is: " + pq.extractMax());
//        System.out.println("Next Biggest is: " + pq.extractMax());
//        System.out.println("Next Biggest is: " + pq.extractMax());
    }
}
