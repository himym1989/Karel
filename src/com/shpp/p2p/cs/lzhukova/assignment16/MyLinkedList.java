package com.shpp.p2p.cs.lzhukova.assignment16;

import java.util.Arrays;

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


// Methods:
// - add (by index) +;
// - add +;
// - addLast +;
// - addFirst +;
// - peek +
// - peekFirst +
// - peekLast +
// - poll
// - pollFirst
// - pollLast
// - get(index)
// - isEmpty +
// - clear +


public class MyLinkedList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;

    public MyLinkedList() {
        size = 0;
        clear();
    }

    public void addLast(E data) {
        Node<E> node = new Node<>(data);
        if (last == null) {
            first = last = node;
        } else {
            node.setPrev(last);
            last.setNext(node);
            last = node;
        }
        size++;
    }

    public void add(E data) {
        addLast(data);
    }

    public void addFirst(E data) {
        Node<E> node = new Node<>(data);
        if (last == null) {
            first = last = node;
        } else {
            node.setNext(first);
            first = node;
        }
        size++;
    }

    public void add(int index, E data) {
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

    public E peek() {
        return peekFirst();
    }

    public E peekFirst() {
        return first.getData();
    }

    public E peekLast() {
        return last.getData();
    }

    public E poll() {
        return pollFirst();
    }

    public E pollFirst() {
        Node<E> node = first;
        if (node != null) {
            first = first.getNext();
            size--;
            return node.getData();
        }
        return null;
    }

    public E pollLast() {
        Node<E> node = last;
        if (node != null) {
            last = last.getPrev();
            size--;
            return node.getData();
        }
        return null;
    }

    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
        }
        if (index == 0) {
            return peekFirst();
        }
        if (index == size - 1) {
            return peekLast();
        } else {
            int i = 0;
            Node<E> node = first;
            while (i < index) {
                node = node.getNext();
                i++;
            }
            return node.getData();
        }

    }

    public String toString() {
        E[] arr = (E[]) new Object[size];
        Node<E> node = first;
        int i = 0;
        while (i < size) {
            arr[i] = (E) node.getData();
            i++;
            node = node.getNext();
        }
        return Arrays.toString(arr);
    }

    public void clear() {
        first = last = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return first == null;
    }
}
