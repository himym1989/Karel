package com.shpp.p2p.cs.lzhukova.assignment11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    MathOperations operations = new MathOperations();
    MathFunctions functions = new MathFunctions();
    private LinkedList<String> expression = new LinkedList<>();
    ExpressionBuffer buffer = new ExpressionBuffer(expression);

    public Double calculate(String mathExpression, HashMap<String, Double> vars) throws Exception {
        expression = mathExpressionAnalyze(mathExpression, vars);
        return evaluation(expression, vars);
    }


    // Grammar:
    // E(expression) -> T{+/-}T;
    // T(term)       -> F{*/*}F;
    // F(factor)     -> N(number)|(E);

    private double evaluation(LinkedList<String> expression, HashMap<String, Double> vars) {
        if (expression.size() == 1) {
            return Double.parseDouble(expression.getFirst());
        } else {
            return parseE();
        }
    }

    private double parseE() {
        double x = parseT();

        while (true) {
            System.out.println("x " + x);
            String operator = buffer.next();

            if (operator.equals("+") || operator.equals("-")) {
                double y = parseT();
                System.out.println("y " + y);
                System.out.println(math(operator, x, y));
                x = math(operator, x, y);
            } else {
                buffer.back();
                return x;
            }
        }
    }


    private double parseT() {
        double x = Double.parseDouble(buffer.next());
        while (true) {
            String operator = buffer.next();
            if (operator != null && (operator.equals("*") || operator.equals("/"))) {
                double y = Double.parseDouble(buffer.next());
                System.out.println(math(operator, x, y));
                x = math(operator, x, y);
            } else {
                buffer.back();
                return x;
            }
        }


    }

    private LinkedList<String> mathExpressionAnalyze(String mathExpression, HashMap<String, Double> vars) throws Exception {

        /* this classes are used for finding matches in the math expression with the following patterns - regexp.
         * Nothing redundant will be saved to the arraylist with the expression - just numbers, variables,
         * operations and functions */
        Pattern pattern = Pattern.compile(functions.buildRegexp() + "|" + "((\\d*\\.?\\d+)|([a-z]+)|" + operations.buildRegexp() + ")");
        Matcher matcher = pattern.matcher(mathExpression);

        if (mathExpression.charAt(0) == '-') {
            expression.add("0");
        }

        boolean isNegative = false;
        String previousValue = "";


        while (matcher.find()) {
            String value = matcher.group();

            if (value.equals("-") && (isOperator(previousValue))) {
                isNegative = true;
                continue;
            }
            if (isValue(value)) {
                if (!isVarPresent(value, vars)) {
                    System.err.println("There is no value for variable: \"" + value + "\"");
                    System.exit(-1);
                } else {
                    expression.add(isNegative ? "-" + value : value);
                    isNegative = !isNegative;
                }
            } else if (isNegative) {
                expression.add("-" + value);
                isNegative = false;
            } else {
                expression.add(value);
            }
            previousValue = value;
        }

        System.out.println(expression.toString());
        return expression;
    }

    private boolean isValue(String value) {
        return value.matches("[a-z]+") && !isFunction(value);
    }


    /**
     * This method implements check if the variable in the math expression attends in the hashmap
     * in order to get its value later;
     */
    private boolean isVarPresent(String value, HashMap<String, Double> vars) {
        return !vars.isEmpty() && vars.get(value) != null;
    }


    /**
     * This method checks if the value is an operator or not.
     */
    private boolean isOperator(String val) {
        return val != null && val.matches(operations.buildRegexp());
    }

    /**
     * This method checks if the value is a group operator or not.
     */
    private boolean isGroupOperator(String val) {
        return val != null && (val.equals("(") || val.equals(")"));
    }

    /**
     * This method checks if the value is a function or not.
     */
    private boolean isFunction(String val) {
        return val != null && val.matches(functions.buildRegexp());
    }

    /**
     * Method implements doing math depending on an operator or function;
     *
     * @return double, result of math operation;
     */
    private double math(String operator, double x, double y) {
        if (!operations.map.containsKey(operator)) {
            throw new IllegalStateException("Unexpected operator: " + operator);
        }
        return operations.map.get(operator).eval(x, y);
    }

    private double math(String operator, double x) {
        if (!functions.funcMap.containsKey(operator)) {
            throw new IllegalStateException("Unexpected operator: " + operator);
        }
        return functions.funcMap.get(operator).eval(x);
    }
}
