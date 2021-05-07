package com.shpp.p2p.cs.lzhukova.assignment16;

import java.util.Arrays;
// Methods:
// - add(e element); +
// - add(int index, e element); +
// - get(int counter); +
// - getLength; +


// - remove
// - removeAll +
// - set
// - contains

public class MyArrayList<E> {
    private int DEFAULT_SIZE = 10;
    private Object[] array;
    private int counter;
    private int size;

    public MyArrayList() {
        array = new Object[DEFAULT_SIZE];
        counter = 0;
        size = 0;
    }

    public void add(E element) {
        checkCapacity(counter);
        array[counter] = element;
        size = counter;
        counter++;
    }

    private void checkCapacity(int index) {
        if (size >= array.length || index >= array.length) {
            int newCapacity = array.length + 1;
            array = Arrays.copyOf(array, newCapacity);
//            System.out.println(newCapacity);
        }
    }

    public void add(int index, E element) {
        if (index >= array.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + array.length);
        }
        checkCapacity(index);
        if (array[index] != null) {
            int moveToIndex = array.length - 1;
            while (moveToIndex > index) {
                array[moveToIndex] = array[moveToIndex - 1];
                moveToIndex--;
            }
        }
        array[index] = element;
    }

    public void removeAll() {
        for (int i = array.length - 1; i >= 0; i--) {
            array[i] = null;
        }
        array = Arrays.copyOfRange(array, 0, 0);
    }

    public E get(int index) {
        if (array[index] == null) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        return (E) array[index];
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    public int getLength() {
        return array.length;
    }
}
