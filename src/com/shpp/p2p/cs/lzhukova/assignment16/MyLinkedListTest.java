package com.shpp.p2p.cs.lzhukova.assignment16;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

class MyLinkedListTest {

    @Test
    void add() {
        LinkedList<Integer> expected = new LinkedList();
        MyLinkedList<Integer> myLinkedList = new MyLinkedList();

        // adding 10 elements one by one;
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
    }
}