package main.midlab2.group4.lab.util;

import java.util.NoSuchElementException;

public class ArrayList<E>
        implements List<E> {

    private static final int DEFAULT_CAPACITY = 5;
    private int capacity;
    private transient int trueSize;
    private Object[] elementData;

    public ArrayList() {
        capacity = DEFAULT_CAPACITY;
        this.elementData = new Object[capacity];
        this.trueSize = 0;
    }

    public ArrayList(int size) {
        capacity = size;
        this.elementData = new Object[capacity];
        this.trueSize = 0;
    }

    /**
     * This returns the current size of the list
     *
     * @return size
     */
    @Override
    public int getSize() {
        return trueSize;
    }

    /**
     * This inserts a given data at the end of the list.
     *
     * @param data element to be inserted
     * @throws ListOverflowException in case the list exceeds the available capacity
     */
    @Override
    public void insert(E data) throws ListOverflowException {
        try {
            if (this.trueSize >= this.elementData.length) {
                this.elementData = createNewCopy(elementData, grow());
            }

            this.elementData[trueSize] = data;
            trueSize++;
        } catch (OutOfMemoryError e) {
            throw new ListOverflowException();
        }
    }

    /**
     * This searches and returns the given element in the parameter if it exists.
     *
     * @param data to be searched
     * @return data if it exists
     * @throws NoSuchElementException in case the given element is not in the list
     */
    @Override
    public E getElement(E data) throws NoSuchElementException {
        int indexOfElement = indexOf(data);
        return elementData(indexOfElement);
    }

    @Override
    public E getElement(int pos) throws NoSuchElementException {
        return elementData(pos);
    }

    /**
     * This deletes a given element if it is inside the list.
     *
     * @param data to be deleted
     * @return a boolean that shows if the element is deleted
     */
    @Override
    public boolean delete(E data) {
        int indexOfData = indexOf(data);
        if (indexOfData > -1) {
            removeElement(this.elementData, indexOfData);
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public E remove(int pos) {
        if (pos > -1 && pos < getSize()) {
            E toRemove = (E) elementData[pos];
            removeElement(this.elementData, pos);
            return toRemove;
        }
        return null;
    }


    /**
     * Searches if a certain elem ent exists inside the list.
     *
     * @param data to be searched
     * @return a boolean that shows if the element is inside the list
     */
    @Override
    public boolean search(E data) {
        return indexOf(data) >= 0;
    }

    private int grow() {
        capacity *= 2;
        return capacity;
    }

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    public int indexOf(Object o) {
        final Object[] clonedSet = elementData;
        for (int i = 0; i < this.trueSize; i++) {
            if (o.equals(clonedSet[i])) {
                return i;
            }
        }
        return -1;
    }

    private void removeElement(Object[] data, int pos) {
        final int newSize = trueSize - 1;

        if (newSize > pos)
            if (newSize - pos >= 0) System.arraycopy(data, pos + 1, data, pos, newSize - pos);

        data[trueSize = newSize] = null;
    }

    private Object[] createNewCopy(Object[] arr, int newSize) {
        Object[] newArr = new Object[newSize];
        System.arraycopy(arr, 0, newArr, 0, arr.length);
        return newArr;
    }

    public void set(int index, E data){
        if (getSize()==0) throw new IndexOutOfBoundsException("index: "+index+" Array Length: "+ getSize());
        elementData[index] = data;
    }

    public String toString() {
        if (trueSize == 0) return "[ ]";
        if (trueSize == 1) return "[ " + elementData[0] + " ]";
        StringBuilder sb = new StringBuilder();

        sb.append("[ ").append(elementData[0].toString());
        for (int i = 1; i < trueSize-1; i++) {
            var item = elementData[i];
            sb.append(", ").append(item.toString());
        }
        sb.append((", ")).append(elementData[trueSize - 1]).append(" ]");

        return sb.toString();
    }
}
