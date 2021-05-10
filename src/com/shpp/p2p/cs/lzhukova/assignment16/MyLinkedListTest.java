package com.shpp.p2p.cs.lzhukova.assignment16;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MyLinkedListTest {

    @Test
    public void addTest() {
        LinkedList<Integer> expected = new LinkedList<>();
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();

        // test isEmpty before adding elements;
        assertEquals(expected.isEmpty(), myLinkedList.isEmpty());

        // adding 100 elements one by one;
        for (int i = 0; i < 100; i++) {
            myLinkedList.add(i);
            expected.add(i);
        }
        assertEquals(expected.toString(), myLinkedList.toString());

        // adding elements at the first position;
        for (int i = 0; i < 10; i++) {
            myLinkedList.addFirst(i);
            expected.addFirst(i);
        }
        assertEquals(expected.toString(), myLinkedList.toString());

        // adding elements at the index;
        for (int i = 0; i < expected.size(); i++) {
            if (i % 2 == 0) {
                myLinkedList.add(i, i);
                expected.add(i, i);
            }
        }
        assertEquals(expected.toString(), myLinkedList.toString());
        // test isEmpty after adding elements;
        assertEquals(expected.isEmpty(), myLinkedList.isEmpty());
    }

    @Test
    public void peekAndPollTest() {
        LinkedList<Integer> expected = new LinkedList<>();
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();

        assertNull(myLinkedList.pollFirst());
        assertNull(myLinkedList.pollLast());


        // adding 100 elements one by one;
        for (int i = 0; i < 100; i++) {
            myLinkedList.add(i);
            expected.add(i);
        }

        // test peek elements from the last and first positions;
        assertEquals(expected.peekFirst(), myLinkedList.peekFirst());
        assertEquals(expected.peek(), myLinkedList.peek());
        assertEquals(expected.peekLast(), myLinkedList.peekLast());
        assertEquals(expected.toString(), myLinkedList.toString());

        // adding elements at the index;
        for (int i = 0; i < expected.size(); i++) {
            if (i % 2 != 0) {
                myLinkedList.add(i, i);
                expected.add(i, i);
            }
        }

        // test poll elements from the last and first positions;
        assertEquals(expected.toString(), myLinkedList.toString());
        assertEquals(expected.poll(), myLinkedList.poll());
        assertEquals(expected.size(), myLinkedList.size());
        assertEquals(expected.toString(), myLinkedList.toString());
        assertEquals(expected.pollFirst(), myLinkedList.pollFirst());
        assertEquals(expected.size(), myLinkedList.size());
        assertEquals(expected.toString(), myLinkedList.toString());
        assertEquals(expected.pollLast(), myLinkedList.pollLast());
        assertEquals(expected.size(), myLinkedList.size());
        assertEquals(expected.toString(), myLinkedList.toString());

        // test clear method
        myLinkedList.clear();
        expected.clear();

        //noinspection ConstantConditions
        assertEquals(expected.isEmpty(), myLinkedList.isEmpty());
        assertEquals(expected.size(), myLinkedList.size());
    }

    @Test
    public void getTest() {
        LinkedList<Integer> expected = new LinkedList<>();
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();

        // adding 100 elements one by one;
        for (int i = 0; i < 100; i++) {
            myLinkedList.add(i);
            expected.add(i);
        }

        assertEquals(expected.getFirst(), myLinkedList.getFirst());
        assertEquals(expected.get(50), myLinkedList.get(50));
        assertEquals(expected.getLast(), myLinkedList.getLast());
        assertEquals(expected.toString(), myLinkedList.toString());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsExceptionByAdding() {
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
        myLinkedList.add(4,5);

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsExceptionByGetting() {
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
        myLinkedList.add(1);
        myLinkedList.add(1);
        myLinkedList.add(1);
        myLinkedList.add(1);
        myLinkedList.get(4);

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsExceptionByGettingFirstEl() {
        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
        myLinkedList.getFirst();
    }
}