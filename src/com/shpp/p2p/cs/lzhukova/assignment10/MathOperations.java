package com.shpp.p2p.cs.lzhukova.assignment10;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * This class contains math operations, their implementation and priority;
 */
class MathOperations {

    Map<String, MathOperation> map = new HashMap<>();

    MathOperations() {
        add(new Addition());
        add(new Division());
        add(new Subtraction());
        add(new Exponentiation());
        add(new Multiplication());
    }


    /**
     * Adding a math operation to the hashmap, which keeps all the operations;
     */
    void add(MathOperation op) {
        map.put(op.getOperator(), op);
    }
}

/**
 * abstract class, that describes common methods for all math operations;
 */
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





