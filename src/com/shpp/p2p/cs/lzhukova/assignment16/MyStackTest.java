package com.shpp.p2p.cs.lzhukova.assignment16;

import org.junit.Test;

import java.util.EmptyStackException;
import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class MyStackTest {
    @Test
    public void pushPopPeek() {
        MyStack<Integer> myStack = new MyStack<>();
        Stack<Integer> stack = new Stack<>();

        assertEquals(true, myStack.isEmpty());
        assertEquals(0, myStack.size());

        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 100; k++) {
                myStack.push(k);
                stack.push(k);
            }
            assertEquals(stack.toString(), myStack.toString());
            assertEquals(stack.size(), myStack.size());
            for (int l = 0; l < 10; l++) {
                assertEquals(stack.pop(), myStack.pop());
            }
            assertEquals(stack.size(), myStack.size());
            assertEquals(stack.peek(), myStack.peek());
            assertEquals(stack.size(), myStack.size());
            assertEquals(stack.toString(), myStack.toString());
        }

    }

    @Test(expected = EmptyStackException.class)
    public void testStackExceptionsByPop() {
        MyStack<Integer> myStack = new MyStack<>();
        myStack.pop();
    }

    @Test(expected = EmptyStackException.class)
    public void testStackExceptionsByPeek() {
        MyStack<Integer> myStack = new MyStack<>();
        myStack.peek();
    }

}