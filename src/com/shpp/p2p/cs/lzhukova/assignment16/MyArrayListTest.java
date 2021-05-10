package com.shpp.p2p.cs.lzhukova.assignment16;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MyArrayListTest {

    @Test
    public void AddSetGetRemove() {
        MyArrayList<Integer> myArrayList = new MyArrayList();
        ArrayList<Integer> expected = new ArrayList<>();

        assertEquals(0, myArrayList.size());

        for (int i = 0; i < 10; i++) {
            myArrayList.add(i);
            expected.add(i);
        }
        // test arrayList after adding elements
        assertEquals(expected.toString(), myArrayList.toString());

        for (int i = 0; i < myArrayList.size(); i++) {
            if (i % 2 == 0) {
                myArrayList.set(i, 0);
                expected.set(i, 0);
            }
        }
        assertEquals(expected.toString(), myArrayList.toString());

        // test adding elements in the middle of arrayList
        for (int i = 5; i < 10; i++) {
            myArrayList.add(i, i);
            expected.add(i, i);
        }
        assertEquals(expected.toString(), myArrayList.toString());
        // test size after "add" and "set" - methods
        assertEquals(expected.size(), myArrayList.size());
        // test correct element at the position
        assertEquals(expected.get(8), myArrayList.get(8));
        assertEquals(expected.get(0), myArrayList.get(0));
        assertEquals(expected.get(expected.size() - 1), myArrayList.get(myArrayList.size() - 1));

        // test removing elements;
        for (int i = expected.size(); i < expected.size() / 2; i--) {
            if (i % 2 == 0) {
                myArrayList.remove(i);
                expected.remove(i);
            }
        }
        assertEquals(expected.toString(), myArrayList.toString());
        // test size after removing elements;
        assertEquals(expected.size(), myArrayList.size());

    }


    @Test
    public void isEmpty() {
        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        assertEquals(true, myArrayList.isEmpty());
        myArrayList.add(1);
        assertEquals(false, myArrayList.isEmpty());
        myArrayList.clear();
        assertEquals(true, myArrayList.isEmpty());

    }

    @Test
    public void contains() {
        MyArrayList<Boolean> myArrayList = new MyArrayList<>();
        assertEquals(false, myArrayList.contains(true));
        myArrayList.add(true);
        myArrayList.add(false);
        assertEquals(true, myArrayList.contains(true));
        assertEquals(true, myArrayList.contains(false));
        myArrayList.clear();
        assertEquals(false, myArrayList.contains(false));

    }

    @Test
    public void indexOf() {
        MyArrayList<String> myArrayList = new MyArrayList<>();
        // test indexOf and lastIndexOf in the empty array list
        assertEquals(-1, myArrayList.indexOf("one"));
        assertEquals(-1, myArrayList.lastIndexOf("one"));

        myArrayList.add("one");
        myArrayList.add("one");
        myArrayList.add("one");

        // test indexOf and lastIndexOf after adding elements;
        assertEquals(0, myArrayList.indexOf("one"));
        assertEquals(2, myArrayList.lastIndexOf("one"));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsExceptionByAdding() {
        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        myArrayList.add(4, 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsExceptionBySetting() {
        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        myArrayList.set(0, 3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsExceptionByGetting() {
        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        myArrayList.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIndexOutOfBoundsExceptionByRemoving() {
        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        myArrayList.remove(0);
    }

}
