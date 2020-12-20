package com.shpp.p2p.cs.lzhukova.assignment11;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class MathOperations {
    Map<String, MathOperation> map = new HashMap<>();

    MathOperations() {
        add(new Addition());
        add(new Division());
        add(new Subtraction());
        add(new Exponentiation());
        add(new Multiplication());
    }

    String getRegexp() {
        Set<String> keys = map.keySet();
        StringBuilder regexp = new StringBuilder("[");
        for (String key : keys) {
            regexp.append("\\").append(key);
        }
        regexp.append("]");

        return regexp.toString();
    }

    void add(MathOperation op) {
        map.put(op.getOperator(), op);
    }
}


abstract class MathOperation {
    public abstract int getPriority();

    public abstract String getOperator();

    public abstract double calc(double x, double y);
}

class Addition extends MathOperation {

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getOperator() {
        return "+";
    }

    @Override
    public double calc(double x, double y) {
        return x + y;
    }
}

class Division extends MathOperation {

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getOperator() {
        return "/";
    }

    @Override
    public double calc(double x, double y) {
        return x / y;
    }

}

class Exponentiation extends MathOperation {

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public String getOperator() {
        return "^";
    }

    @Override
    public double calc(double x, double y) {
        return Math.pow(x, y);
    }
}

class Subtraction extends MathOperation {

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getOperator() {
        return "-";
    }

    @Override
    public double calc(double x, double y) {
        return x - y;
    }
}

class Multiplication extends MathOperation {

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getOperator() {
        return "*";
    }

    @Override
    public double calc(double x, double y) {
        return x * y;
    }
}





