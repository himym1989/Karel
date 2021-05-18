package com.shpp.p2p.cs.lzhukova.assignment17.assignment11;

import java.util.LinkedList;

public class ExpressionBuffer extends LinkedList<String> {
    private int position;

    public String nextToken() {
        if (position == size()) return null;
        return get(position++);
    }

    public void back() {
        position--;
    }
}
