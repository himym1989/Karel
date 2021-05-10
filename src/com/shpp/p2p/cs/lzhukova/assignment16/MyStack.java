package com.shpp.p2p.cs.lzhukova.assignment16;

import java.util.EmptyStackException;

/**
 * Data structure, based on MyLinkedList and on the LIFO(Last-In-First-Out) principle;
 * The main feature of MyStack is a probability to do actions with the top of the stack,
 * in fact with the last added element;
 * The next methods are implemented:
 * - isEmpty();
 * - push(e Element);
 * - pop();
 * - peek();
 * - size();
 * - toString();
 *
 * @param <E> - type of the data, stored in elements of the stack;
 */
public class MyStack<E> {

    private final MyLinkedList<E> linkedList = new MyLinkedList<>();
    private int size = 0;

    /**
     * @return true - if the first element is null, false - if not;
     */
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    /**
     * Method implements adding an element at the top of the stack;
     *
     * @param element - an element to add;
     */
    public void push(E element) {
        linkedList.add(element);
        size = linkedList.size();
    }

    /**
     * Method implements  removing and returning an element from the top of the stack;
     * If stack is empty, an exception is thrown;
     *
     * @return - element of type E from the top of the stack;
     */
    public E pop() {
        if (linkedList.isEmpty()) {
            throw new EmptyStackException();
        }
        E element = linkedList.pollLast();
        size = linkedList.size();
        return element;
    }

    /**
     * Method implements returning an element from the top of the stack without removing it;
     * If stack is empty, an exception is thrown;
     *
     * @return - element of type E from the top of the stack;
     */
    public E peek() {
        if (linkedList.isEmpty()) {
            throw new EmptyStackException();
        }
        return linkedList.peekLast();
    }

    /**
     * @return size of the stack(amount of elements);
     */
    public int size() {
        return linkedList.size();
    }


    public String toString() {
        return linkedList.toString();
    }
}
