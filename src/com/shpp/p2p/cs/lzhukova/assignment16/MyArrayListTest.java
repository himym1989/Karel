package com.shpp.p2p.cs.lzhukova.assignment16;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MyArrayListTest {

    @Test
    public void testAddSetGetRemove() {
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
        MyArrayList<Integer> arrayList = new MyArrayList<>();
        assertEquals(true, arrayList.isEmpty());
        arrayList.add(1);
        assertEquals(false, arrayList.isEmpty());
        arrayList.clear();
        assertEquals(true, arrayList.isEmpty());

    }

    @Test
    public void contains() {
        MyArrayList<Boolean> arrayList = new MyArrayList<>();
        assertEquals(false, arrayList.contains(true));
        arrayList.add(true);
        arrayList.add(false);
        assertEquals(true, arrayList.contains(true));
        assertEquals(true, arrayList.contains(false));
        arrayList.clear();
        assertEquals(false, arrayList.contains(false));

    }
}
