package com.shpp.p2p.cs.lzhukova.assignment17.assignment11OnMyCollections;

import com.shpp.p2p.cs.lzhukova.assignment17.MyLinkedList;


public class ExpressionBuffer extends MyLinkedList<String> {
    private int position;

    public String nextToken() {
        if (position == size()) return null;
        return get(position++);
    }

    public void back() {
        position--;
    }
}
