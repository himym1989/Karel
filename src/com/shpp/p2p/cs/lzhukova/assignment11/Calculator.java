package com.shpp.p2p.cs.lzhukova.assignment11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    HashMap<String, Double> vars = new HashMap<>();
    MathOperations operations = new MathOperations();
    MathFunctions functions = new MathFunctions();
    private LinkedList<String> expression = new LinkedList<>();
    ExpressionBuffer buffer = new ExpressionBuffer(expression);

    public double calculate(String mathExpression, String[] args) {
        vars = extractVars(args);
        expression = mathExpressionAnalyze(mathExpression);
        return evaluation(expression);
    }

    /**
     * This method implements extracting variables and their values from program arguments
     * and putting them to the hashmap;
     *
     * @param args - String array from the command line;
     * @return vars, Hashmap with vars and values;
     */
    private HashMap<String, Double> extractVars(String[] args)  {
        for (int i = 1; i < args.length; i++) {
            args[i] = args[i].replaceAll(" ", "");
            String[] buffer = args[i].split("=");

            if (buffer.length <= 1) {
                System.err.println("Check your variables. Maybe you haven't input some variable's value.");
                System.exit(-1);
            } else {
                String var = buffer[0].toLowerCase();
                double value = Double.parseDouble(buffer[1]);
                vars.put(var, value);
            }
        }
        return vars;
    }

    // Grammar:
    // E(expression) -> T{+/-}T;
    // T(term)       -> F{*//}F;
    // T2            -> F{^}F;
    // F(factor)     -> N(number)|(E);

    private double evaluation(LinkedList<String> expression) {
        if (expression.size() == 1) {
            return Double.parseDouble(expression.getFirst());
        } else {
            return parseE();
        }
    }

    private double parseE() {
        double x = parseT();

        while (true) {
            String operator = buffer.nextToken();

            if (operator.equals("+") || operator.equals("-")) {
                double y = parseT();
                x = math(operator, x, y);
            } else {
                buffer.back();
                return x;
            }

        }
    }


    private double parseT() {
        double x = parseT2();

        while (true) {
            String operator = buffer.nextToken();
            if (operator != null && (operator.equals("*") || operator.equals("/"))) {
                double y = parseT2();
                x = math(operator, x, y);
            } else {
                buffer.back();
                return x;
            }
        }
    }

    private double parseT2() {
        double x = parseF();
        while (true) {
            String operator = buffer.nextToken();
            if (operator != null && operator.equals("^")) {
                double y = parseF();
                x = math(operator, x, y);
            } else {
                buffer.back();
                return x;
            }
        }
    }

    private double parseF() {
        String f = buffer.nextToken();

        while (true) {
            double a;
            if(isVariable(f)){
                return vars.get(f);
            }
            if (isFunction(f)) {
                double x = parseF();
                return math(f, x);
            }
            if (f.equals("(")) {
                a = parseE();
                if (buffer.nextToken().equals(")")) {
                    return a;
                }
            } else if (f.equals("-")) {
                double x = parseF();
                if (x > 0) {
                    return Double.parseDouble("-" + x);
                } else {
                    return 0 - x;
                }
            } else {
                return Double.parseDouble(f);
            }
        }
    }


    private LinkedList<String> mathExpressionAnalyze(String mathExpression) {

        /* this classes are used for finding matches in the math expression with the following patterns - regexp.
         * Nothing redundant will be saved to the arraylist with the expression - just numbers, variables,
         * operations and functions */
        Pattern pattern = Pattern.compile(functions.buildRegexp() + "|" + "((\\d*\\.?\\d+)|([a-z]+)|" + operations.buildRegexp() + ")");
        Matcher matcher = pattern.matcher(mathExpression);


        while (matcher.find()) {
            String value = matcher.group();

            if (isVariable(value)) {
                if (!isVarPresent(value)) {
                    System.err.println("There is no value for variable: \"" + value + "\"");
                    System.exit(-1);
                }
                else{
                    expression.add(value);
                }
            } else {
                expression.add(value);
            }
        }
        bracketsCheck();

        return expression;
    }

    private void bracketsCheck() {
        int openBracket = 0;
        int closeBracket = 0;
        for (String s : expression) {
            if (s.equals("(")) {
                openBracket += 1;
            }
            if (s.equals(")")) {
                closeBracket += 1;
            }
        }
        if (openBracket != closeBracket) {
            System.err.println("You forgot about some bracket! Check your expression and try again");
            System.exit(-1);
        }
    }

    private boolean isVariable(String value) {
        return value.matches("[a-z]+") && !isFunction(value);
    }


    /**
     * This method implements check if the variable in the math expression attends in the hashmap
     * in order to get its value later;
     */
    private boolean isVarPresent(String value) {
        return !vars.isEmpty() && vars.get(value) != null;
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
