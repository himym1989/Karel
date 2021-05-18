package com.shpp.p2p.cs.lzhukova.assignment17;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * This class implements element of MyLinkedList;
 *
 * @param <E> - type of the element's data;
 */
class Node<E> {
    private final E data;
    private Node<E> next;

    private Node<E> prev;

    Node(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public Node<E> getPrev() {
        return prev;
    }

    public void setPrev(Node<E> prev) {
        this.prev = prev;
    }

    public boolean hasNext() {
        return getNext() != null;
    }
}


/**
 * MyLinkedList implements doubly linked list of Nodes;
 * The feature of this data structure is that it doesn't has fixed size.
 * Each node has an access to its previous and next element - in this way is their coherence implemented;
 * The main requirement is a probability to work with the first and last elements of the list;
 * Though getting and adding an element at the specific index are also implemented;
 * MyLinkedList has next methods:
 * - add (by index);
 * - add;
 * - addLast;
 * - addFirst;
 * - peek;
 * - peekFirst;
 * - peekLast;
 * - poll;
 * - pollFirst;
 * - pollLast;
 * - get(index);
 * - isEmpty;
 * - clear;
 *
 * @param <E> - type of data, stored in elements of linked list - Nodes;
 */
public class MyLinkedList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;

    public MyLinkedList() {
        clear();
    }

    /**
     * Method implements adding element at the end of the list. In fact,
     * new element is added as a next element to the former last element;
     * after that new added element becomes the last;
     *
     * @param data - data, that will be stored in a new added element;
     */
    public void addLast(E data) {
        Node<E> node = new Node<>(data);
        if (first == null && last == null) {
            first = last = node;
        } else {
            node.setPrev(last);
            last.setNext(node);
            last = node;
        }
        size++;
    }

    /**
     * Method is equal to "addLast(E data)";
     *
     * @param data - data, that will be stored in a new added element;
     */
    public void add(E data) {
        addLast(data);
    }

    /**
     * Method implements adding element at the beginning of the list. In fact,
     * new element is added as a previous element to the former first one;
     * after that new added element becomes the first;
     *
     * @param data - data, that will be stored in a new added element;
     */
    public void addFirst(E data) {
        Node<E> node = new Node<>(data);
        if (first == null && last == null) {
            first = last = node;
        } else {
            node.setNext(first);
            first = node;
        }
        size++;
    }

    /**
     * Method implements adding element at the specific index. New added element get a reference
     * to the element at the previous index. And the formed element at the index becomes the next one
     * in relation to new added element;
     * if index is bigger than size of the list, an exception is thrown;
     *
     * @param data - data, that will be stored in a new added element;
     */
    public void add(int index, E data) {
        if (index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            addFirst(data);
            return;
        }
        if (index == size) {
            addLast(data);
            return;
        }

        Node<E> newNode = new Node<>(data);
        int i = 0;
        Node<E> oldNode = first;
        while (i < index) {
            if (i == index - 1) {
                newNode.setPrev(oldNode);
                newNode.setNext(oldNode.getNext());
                oldNode.setNext(newNode);
            }
            oldNode = oldNode.getNext();
            i++;
        }
        size++;
    }

    /**
     * @return - first element of the list, without removing it, or null, if the list is empty;
     */
    public E peekFirst() {
        if (size == 0) {
            return null;
        }
        return first.getData();

    }

    /**
     * @return - first last of the list, without removing it, or null, if the list is empty;
     */
    public E peekLast() {
        if (size == 0) {
            return null;
        }
        return last.getData();
    }

    /**
     * Method is equal to "peekFirst";
     *
     * @return - first element of the list, without removing it, or null, if the list is empty;
     */
    public E peek() {
        return peekFirst();
    }

    /**
     * @return - first element of the list, removing it, or null, if the list is empty
     */
    public E pollFirst() {
        if (first != null) {
            E data = first.getData();
            if (size == 1) {
                last = first = null;
            } else {
                first = first.getNext();
            }
            size--;
            return data;
        }
        return null;
    }

    /**
     * @return - last element of the list, removing it, or null, if the list is empty
     */
    public E pollLast() {
        if (last != null) {
            E data = last.getData();
            if (size == 1) {
                last = first = null;
            } else {
                last = last.getPrev();
            }
            size--;
            return data;
        }
        return null;
    }

    /**
     * Method is equal to "pollFirst";
     *
     * @return - first element of the list, removing it, or null, if the list is empty;
     */
    public E poll() {
        return pollFirst();
    }


    /**
     * Method returns data of an element at the specific index, without removing it;
     * If index is equal to size or bigger, than a list size, an exception is thrown;
     *
     * @param index - int, index of the element to be returned;
     * @return - element of the list;
     */
    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
        }
        if (index == 0) {
            return getFirst();
        }
        if (index == size - 1) {
            return getLast();
        }
        int i = 0;
        Node<E> node = first;
        while (i < index) {
            node = node.getNext();
            i++;
        }
        return node.getData();
    }


    public boolean contains(E element) {
        if (first == null) return false;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(element, get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method returns data of an element at the last index, without removing it;
     * If list is empty, an exception is thrown;
     *
     * @return - element of the list;
     */
    public E getLast() {
        if (last == null) {
            throw new IndexOutOfBoundsException("The list is empty");
        }
        return last.getData();
    }

    /**
     * Method returns data of an element at the first index, without removing it;
     * If list is empty, an exception is thrown;
     *
     * @return - element of the list;
     */
    public E getFirst() {
        if (first == null) {
            throw new IndexOutOfBoundsException("The list is empty");
        }
        return first.getData();
    }

    /**
     * Method helps to print a list as a string, saving elements' data to an array;
     */
    public String toString() {
        E[] arr = (E[]) new Object[size];
        Node<E> node = first;

        if (first == null) {
            return "[]";
        }

        int i = 0;
        while (i < size) {
            arr[i] = node.getData();
            i++;
            if (node.hasNext()) {
                node = node.getNext();
            }
        }
        return Arrays.toString(arr);
    }

    /**
     * Reset first and last elements and list's size;
     */
    public void clear() {
        first = last = null;
        size = 0;
    }

    /**
     * @return size of the linked list, in fact amount of the linked nodes;
     */
    public int size() {
        return size;
    }

    /**
     * @return true - if the first element is null, false - if not;
     */
    public boolean isEmpty() {
        return first == null;
    }


    public Iterator<E> iterator() {
        return new Iterator<>() {
            Node<E> node = first;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public E next() {
                E currentData = node.getData();
                node = node.getNext();
                return currentData;
            }
        };
    }

}
