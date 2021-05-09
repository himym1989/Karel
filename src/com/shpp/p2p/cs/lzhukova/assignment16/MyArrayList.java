package com.shpp.p2p.cs.lzhukova.assignment16;

import java.util.Arrays;
import java.util.function.Function;


/**
 * This class implements data structure based on array, but with advanced features;
 * At first, it hasn't got fixed length(though it has default capacity, that increases, when the array list is full);
 * Methods for adding and deleting elements are also implemented;
 * The following methods are implemented in this class:
 * add(E element);
 * add(int index, E element);
 * get(int index);
 * remove(int index);
 * removeAll;
 * set(int index);
 * size();
 * contains(E element);
 * isEmpty() ;
 * indexOf(E element);
 * lastIndexOf(E element);
 *
 * @param <E> - type of element of the list;
 */
public class MyArrayList<E> {
    private Object[] array;
    private int size;

    public MyArrayList() {
        clear();
    }

    /**
     * Method implements adding element in the end of the list;
     * checks capacity before adding;
     *
     * @param element - element to add to the arraylist of type E;
     */
    public void add(E element) {
        checkCapacity(size);
        array[size] = element;
        size++;
    }

    /**
     * Check if the arraylist is capable of storing new elements;
     * increases capacity if is needed;
     *
     * @param index - int, index of the element to add to the arraylist;
     */
    private void checkCapacity(int index) {
        if (size >= array.length - 1 || index >= array.length - 1) {
            int capacity = (int) Math.ceil(array.length * 1.5 + 1);
            array = Arrays.copyOf(array, capacity);
        }
    }

    /**
     * Method implements adding elements at the specific index;
     * When this method is called, all the elements beginning from the old element at the index and till the end of the
     * arraylist are moved to one cell right, "clearing space" for a new element;
     * if index > length of the arraylist, an exception is thrown;
     *
     * @param index   - int, index of the element to add to the arraylist;
     * @param element - E, element to be added;
     */
    public void add(int index, E element) {
        if (index > array.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + array.length);
        }
        checkCapacity(index);
        int moveToIndex = array.length - 1;
        while (moveToIndex > index) {
            array[moveToIndex] = array[moveToIndex - 1];
            moveToIndex--;
        }

        array[index] = element;
        size++;
    }

    /**
     * Method implements setting new value to the element;
     *
     * @param index   - int, index of the cell, which value will be changed;
     * @param element - E, new value of the
     */
    public void set(int index, E element) {
        if (index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        array[index] = element;
    }

    /**
     * Method returns element at the specific index, but doesn't removes it;
     *
     * @param index - int, index of the cell, which value will be returned;
     * @return E - element;
     */
    public E get(int index) {
        return (E) array[index];
    }

    /**
     * Method removes element at the specific index;
     * In this case all elements, beginning from the next element of removed one and till the end of the arraylist
     * are moved one cell left;
     *
     * @param index - int, index of the cell, which value will be removed;
     */
    public void remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        while (index != size - 1) {
            array[index] = array[index + 1];
            index++;
        }
        size--;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(array, 0, size));
    }

    /**
     * @return size of the arraylist(can differ from capacity, in fact, returns position of last added element);
     */
    public int size() {
        return size;
    }

    /**
     * Method checks if the arraylist contains an element;
     *
     * @param element - E, element to be searched for;
     * @return - boolean, true - if arraylist contains an element, false - if doesn't;
     */
    public boolean contains(E element) {
        for (Object o : array) {
            if (o == element) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param element - E, element, which first index will be returned;
     * @return - int, first index of element;
     * returns -1, if arraylist contains no such element;
     */
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param element - E, element, which last index will be returned;
     * @return - int, last index of element;
     * returns -1, if arraylist contains no such element;
     */
    public int lastIndexOf(E element) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i] == element) {
                return i;
            }
        }
        return -1;
    }

    /**
     * creates new clear arraylist with zero length;
     */
    public void clear() {
        array = new Object[0];
        size = 0;
    }

    /**
     * @return true, if size of array is zero, in other cases - false;
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * calling some function for each element of the arraylist;
     *
     * @param fn - function to be called;
     */
    public void forEach(Function<E, Void> fn) {
        for (int i = 0; i < size; i++) {
            fn.apply((E) array[i]);
        }
    }
}
