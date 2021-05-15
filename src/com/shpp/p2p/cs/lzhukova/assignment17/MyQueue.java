package com.shpp.p2p.cs.lzhukova.assignment17;

import java.util.NoSuchElementException;

/**
 * Data structure, based on MyLinkedList and on the FIFO(First-In-First-Out) principle;
 * Elements are added at the end of the queue, but are removed from the beginning. The main feature of MyQueue
 * is a probability to do actions with the beginning of the queue - in fact with the first element;
 * The next methods are implemented:
 * -add(E element);
 * - peek();
 * - poll();
 * - element();
 * - size();
 * - toString();
 * - clear();
 *
 * @param <E> - type of the data, stored in elements of the queue;
 */
public class MyQueue<E> {
    private final MyLinkedList<E> linkedList = new MyLinkedList<>();
    private int size;

    /**
     * Method implements adding element at the end of the queue;
     *
     * @param element - element of type E to add;
     */
    public void add(E element) {
        linkedList.add(element);
        size = linkedList.size();
    }


    /**
     * Method implements returning first element of the queue without removing;
     * if the queue is empty, returns null;
     *
     * @return - peeked element of type E;
     */
    public E peek() {
        return linkedList.peekFirst();
    }

    /**
     * Method implements removing and returning first element of the queue;
     * if the queue is empty, returns null;
     *
     * @return - removed element of type E;
     */
    public E poll() {
        E element = linkedList.pollFirst();
        size = linkedList.size();
        return element;
    }

    /**
     * Method implements returning first element of the queue without removing;
     * if the queue is empty, an exception is thrown;
     *
     * @return - peeked element of type E;
     */
    public E element() {
        if (linkedList.isEmpty()) {
            throw new NoSuchElementException();
        }
        return linkedList.peekFirst();
    }

    /**
     * @return - size of the queue;
     */
    public int size() {
        return linkedList.size();
    }

    public String toString() {
        return linkedList.toString();
    }

    public void clear() {
        linkedList.clear();
    }
}
