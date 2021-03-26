package com.shpp.p2p.cs.lzhukova.assignment11;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * This class contains all the math functions, their priority and implementation.
 */
class MathFunctions {
    Map<String, MathFunction> funcMap = new HashMap<>();

    MathFunctions() {
        add(new MathCos());
        add(new MathSin());
        add(new MathTan());
        add(new MathATan());
        add(new MathLog2());
        add(new MathLog10());
        add(new MathSqrt());
    }

    // method builds a regular expression with math functions
    String buildRegexp() {
        Set<String> keys = funcMap.keySet();
        StringBuilder regexp = new StringBuilder("(");

        for (String key : keys) {
            regexp.append(key).append("|");
        }
        regexp.setLength(regexp.length() - 1);
        regexp.append(")");

        return regexp.toString();
    }

    /**
     * Adding a math function to the hashmap, which keeps all the functions;
     */
    void add(MathFunction func) {
        funcMap.put(func.getFunction(), func);
    }
}

/**
 * abstract class, that describes common methods for all math functions;
 */
abstract class MathFunction {
    public abstract int getPriority();

    public abstract String getFunction();

    public abstract double eval(double x);
}

class MathSin extends MathFunction {

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public String getFunction() {
        return "sin";
    }

    @Override
    public double eval(double x) {
        return Math.sin(x);
    }
}

class MathCos extends MathFunction {

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public String getFunction() {
        return "cos";
    }

    @Override
    public double eval(double x) {
        return Math.cos(x);
    }
}

class MathTan extends MathFunction {

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public String getFunction() {
        return "tan";
    }

    @Override
    public double eval(double x) {
        return Math.tan(x);
    }
}

class MathATan extends MathFunction {

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public String getFunction() {
        return "atan";
    }


    @Override
    public double eval(double x) {
        return Math.atan(x);
    }
}

class MathLog10 extends MathFunction {

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public String getFunction() {
        return "log10";
    }

    @Override
    public double eval(double x) {
        return Math.log10(x);
    }
}

class MathLog2 extends MathFunction {

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public String getFunction() {
        return "log2";
    }

    @Override
    public double eval(double x) {
        return Math.log(x) / Math.log(2);
    }
}

class MathSqrt extends MathFunction {

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public String getFunction() {
        return "sqrt";
    }

    @Override
    public double eval(double x) {
        return Math.sqrt(x);
    }
}