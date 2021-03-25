package com.shpp.p2p.cs.lzhukova.assignment11;

import java.util.LinkedList;

public class ExpressionBuffer {
    public LinkedList<String> expression;
    private int position;

    public ExpressionBuffer(LinkedList<String> expression) {
        this.expression = expression;
    }

    public String next() {
        if(position==expression.size()) return null;
        return expression.get(position++);
    }

    public int getPosition() {
        return position;
    }

    public void back() {
        position--;
    }
}
