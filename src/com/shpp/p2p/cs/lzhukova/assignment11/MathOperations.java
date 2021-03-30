package com.shpp.p2p.cs.lzhukova.assignment11;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * This class contains math operations and their evaluation;
 */
class MathOperations {

    Map<String, MathOperation> map = new HashMap<>();

    MathOperations() {
        add(new Addition());
        add(new Subtraction());
        add(new Multiplication());
        add(new Division());
        add(new Exponentiation());
    }

    /**
     * Method implements building regular expression for all operators;
     */
    String buildRegexp() {
        Set<String> keys = map.keySet();
        StringBuilder regexp = new StringBuilder("[");
        for (String key : keys) {
            regexp.append("\\").append(key);
        }
        regexp.append("]");
        return regexp.toString();
    }

    /**
     * Adding a math operation to the hashmap, which keeps all the operations;
     */
    void add(MathOperation op) {
        map.put(op.getOperator(), op);
    }
}

/**
 * abstract class, that describes common methods for all operations;
 */
abstract class MathOperation {
    public abstract String getOperator();

    public abstract double eval(double x, double y);
}


class Addition extends MathOperation {

    @Override
    public String getOperator() {
        return "+";
    }

    @Override
    public double eval(double x, double y) {
        return x + y;
    }
}


class Subtraction extends MathOperation {

    @Override
    public String getOperator() {
        return "-";
    }

    @Override
    public double eval(double x, double y) {
        return x - y;
    }
}


class Multiplication extends MathOperation {

    @Override
    public String getOperator() {
        return "*";
    }

    @Override
    public double eval(double x, double y) {
        return x * y;
    }
}

class Division extends MathOperation {

    @Override
    public String getOperator() {
        return "/";
    }

    @Override
    public double eval(double x, double y) {
        return x / y;
    }

}


class Exponentiation extends MathOperation {

    @Override
    public String getOperator() {
        return "^";
    }

    @Override
    public double eval(double x, double y) {
        return Math.pow(x, y);
    }
}




