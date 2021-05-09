package com.shpp.p2p.cs.lzhukova.assignment16;

import java.util.Arrays;
import java.util.function.Function;
// Methods:
// - add(e element); +
// - add(int index, e element); +
// - get(int counter); +
// - remove +
// - removeAll +
// - set +
// - size; +
// - contains +
// - isEmpty +

public class MyArrayList<E> {
    private Object[] array;
    private int size;

    public MyArrayList() {
        clear();
    }

    public void add(E element) {
        checkCapacity(size);
        array[size] = element;
        size++;
    }

    private void checkCapacity(int index) {
        if (size >= array.length - 1 || index >= array.length - 1) {
            int newCapacity = (int) Math.ceil(array.length * 1.5 + 1);
            array = Arrays.copyOf(array, newCapacity);
        }
    }

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


    public void set(int index, E element) {
        if (index > size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        array[index] = element;
    }

    public void clear() {
        array = new Object[0];
        size = 0;
    }

    public E get(int index) {
        return (E) array[index];
    }

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

    public int size() {
        return size;
    }

    public boolean contains(E element) {
        for (Object o : array) {
            if (o == element) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void forEach(Function<E, Void> fn) {
        for (int i = 0; i < size; i++) {
            fn.apply((E) array[i]);
        }
    }
}
