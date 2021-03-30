package com.shpp.p2p.cs.lzhukova.assignment11;

import java.util.LinkedList;

public class ExpressionBuffer extends LinkedList<String> {
    private int position;

    public String nextToken() {
        if (position == size()) return null;
        return get(position++);
    }

    public int getPosition() {
        return position;
    }

    public void back() {
        position--;
    }

    public void scanToken() {
            position++;
    }

    public String previousToken() {
        if (position == 0) return null;
        return get(position--);
    }

}
