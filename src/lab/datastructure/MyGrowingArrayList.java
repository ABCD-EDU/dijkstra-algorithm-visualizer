package lab.datastructure;
import java.util.NoSuchElementException;

public class MyGrowingArrayList<E> {

    private E[] theList;
    private int size;
    private int initialCapacity;


    /**
     * Initializes the size at 0, the initial capacity of the array is 5 before it doubles after the size exceeds
     * the value of the initial capacity and lastly the array theList which will have a size of 5
     */
    MyGrowingArrayList() {
        size = 0;
        initialCapacity = 5;
        theList = (E[]) new Object[initialCapacity];
    }

    /**
     * Returns the number of the elements inside the array.
     * @return size
     */

    public int size() {
        return size;
    }

    /**
     * Allows the user to append a value at the end of the array whilst the
     * @param data to be appended

     */

    public void insert(E data)  {
        insert(size,data);
    }

    /**
     * Returns the data itself if the search method returns true
     * @param data to be searched
     * @return the parameter passed
     * @throws NoSuchElementException if no element was found
     */

    public E getElement(E data) throws NoSuchElementException {
        int dataIndex = search(data);
        if (dataIndex ==-1)
            throw new NoSuchElementException("No such element is found in the array");
        return theList[dataIndex];
    }


    /**
     * Allows the user to remove an element from the array where the size of the array is reduced by one
     * and the elements to the right of the removed element shifts 1 index to the left.
     * @param data the element to be removed
     * @return true if deletion is a success
     */

    public boolean delete(E data) {
        if (search(data) == -1) return false;
        delete(search(data));
        return true;
    }


    public void insert(int index, E data) {
        if (size==0){
            theList[0] = data;
        }
        if (index>size){
            throw new IndexOutOfBoundsException("index: "+index+" Array Length: "+ size);
        }

        if (size == initialCapacity) {
            setNewInitialCapacity();
            E[] tempArray = (E[]) new Object[initialCapacity];
            for (int i = 0; i < size; i++) {
                tempArray[i] = theList[i];
            }
            theList = (E[]) new Object[initialCapacity];
            for (int i=0;i<tempArray.length;i++){
                theList[i] = tempArray[i];
            }
            for (int i=size;i>index;i--){
                theList[i] = theList[i-1];
            }
            theList[index] = data;
            size++;
            return;//acts like break but for the method
        }

        for (int i=size;i>index;i--){
            theList[i] = theList[i-1];
        }
        theList[index] = data;
        size++;
    }



    public boolean delete(int index) {
        if (index>=size)
            throw new IndexOutOfBoundsException("index: "+index+" Array Length: "+ size);
        for (int i = 0, k = 0; i <= size - 1; i++) {
            if (i == index) {
                continue;
            }
            theList[k++] = theList[i];
        }
        this.size -= 1;
        theList[size] = null;

        return true;
    }


    public int search(E data) {
        for (int i=0; i<size;i++){
            if (data.equals(theList[i])){
                return i;
            }
        }
        return -1;
    }

    public E search(int index){
        if (index>=size){
            throw new IndexOutOfBoundsException("index: "+index+" Array Length: "+ size);
        }
        return theList[index];
    }

    public E get(int index) throws NoSuchElementException {
        if (index>=size){
            throw new IndexOutOfBoundsException("index: "+index+" Array Length: "+ size);
        }
        return theList[index];
    }

    public void set(int index, E data){
        if (size==0) throw new IndexOutOfBoundsException("index: "+index+" Array Length: "+ size);
        theList[index] = data;
    }


    public E remove(int index) {
        if (index>=size)
            throw new IndexOutOfBoundsException("index: "+index+" Array Length: "+ size);
        E toReturn =null;
        for (int i = 0, k = 0; i <= size - 1; i++) {
            if (i == index) {
                toReturn = theList[i];
                continue;
            }
            theList[k++] = theList[i];
        }
        this.size -= 1;
        theList[size] = null;

        return toReturn;
    }


    /**
     * Doubles the initial capacity of the file
     */
    public void setNewInitialCapacity(){
        this.initialCapacity *= 2;
    }

    @Override
    public String toString() {
        String toReturn ="";
        toReturn+= "Size ["+size+"] | ";
        for (int i=0;i<size;i++){
            toReturn+= theList[i]+" ";
        }
        toReturn += "| Initial Capacity ["+initialCapacity+"]";
        return toReturn;
    }
}
