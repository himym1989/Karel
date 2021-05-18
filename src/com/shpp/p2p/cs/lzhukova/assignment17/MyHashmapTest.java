package com.shpp.p2p.cs.lzhukova.assignment17;

import org.junit.*;

import java.util.HashMap;
import java.util.Iterator;

import static org.junit.Assert.*;

public class MyHashmapTest {

    @Test
    public void Test() {
        HashMap<Integer, Integer> hm = new HashMap<>();
        MyHashmap<Integer, Integer> myHashmap = new MyHashmap<>();
        myHashmap.put(1, 1);
        myHashmap.put(2, 10);
        myHashmap.put(3, 1);
        myHashmap.put(4, 1);
        myHashmap.put(5, 1);
        Integer prevValue = myHashmap.put(1, 10);

        // test size after adding elements;
        assertEquals(5, myHashmap.size());
        assertEquals(Integer.valueOf(1), prevValue);

        // test get();
        assertEquals(Integer.valueOf(10), myHashmap.get(2));

        // test containsKey();
        assertTrue(myHashmap.containsKey(3));
        assertFalse(myHashmap.containsKey(22));

        // test containsValue();
        assertFalse(myHashmap.containsValue(null));
        assertFalse(myHashmap.containsValue(100));
        assertTrue(myHashmap.containsValue(1));


        myHashmap.clear();
        assertTrue(myHashmap.isEmpty());
        assertEquals(0, myHashmap.size());

        // test after clearing the hashmap;
        assertFalse(myHashmap.containsKey(1));
        for (int i = 0; i < 100; i++) {
            myHashmap.put(i, i);
        }
        assertEquals(100, myHashmap.size());
    }
}