package com.shpp.p2p.cs.lzhukova.assignment16;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MyQueueTest {

    @Test
    public void peekPollAdd() {
        MyQueue<Integer> myQueue = new MyQueue<>();

        assertEquals(0, myQueue.size());
        assertNull(myQueue.peek());
        assertNull(myQueue.poll());

        for (int i = 0; i < 10; i++) {
            myQueue.add(i);
        }
        assertEquals(10, myQueue.size());
        assertEquals(0, myQueue.peek());
        assertEquals(0, myQueue.poll());
        assertEquals(9, myQueue.size());
        assertEquals(1, myQueue.peek());
    }

    @Test(expected = NoSuchElementException.class)
    public void NoSuchElementException() {
        MyQueue<Integer> myQueue = new MyQueue<>();
        myQueue.element();
    }

}