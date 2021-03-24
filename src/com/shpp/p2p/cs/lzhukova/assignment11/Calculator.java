package com.shpp.p2p.cs.lzhukova.assignment11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    MathOperations operations = new MathOperations();
    MathFunctions functions = new MathFunctions();

    private ArrayList<String> expression = new ArrayList<>();


    public Double calculate(String mathExpression, HashMap<String, Double> vars) throws Exception {
        expression = mathExpressionAnalyze(mathExpression, vars);
        return doMath(expression, vars);
    }

    private double doMath(ArrayList<String> expression, HashMap<String, Double> vars) {
        while (expression.size() != 1) {
            for (int i = 0; i < expression.size(); i++) {
                String element = expression.get(i);
                if (isOperator(element)) {
                }
            }
        }


        return 0.0;
    }

    private ArrayList<String> mathExpressionAnalyze(String mathExpression, HashMap<String, Double> vars) throws Exception {

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
                    throw new ArithmeticException("There is no value for variable: \"" + value + "\"");
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
        return vars.get(value) != null;
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

}
