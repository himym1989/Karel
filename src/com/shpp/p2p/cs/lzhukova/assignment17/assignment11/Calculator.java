package com.shpp.p2p.cs.lzhukova.assignment17.assignment11;


import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements the calculator directly.
 * It analyzes the input formula, parses it and evaluates;
 */
public class Calculator {
    MathOperations operations = new MathOperations();
    MathFunctions functions = new MathFunctions();

    /**
     * Hashmap, that keeps all the variables with their ;
     */
    HashMap<String, Double> vars = new HashMap<>();
    ExpressionBuffer expressionBuffer = new ExpressionBuffer();

    Calculator(String[] args) {
        extractVars(args);
        mathExpressionAnalyze(args[0]);
    }


    // Grammar:
    // E(expression) -> T{+/-}T;
    // T(term)       -> F{*//}F;
    // T2            -> F{^}F;
    // F(factor)     -> N(number)|(E);
    public double calculate() {
        return parseE();
    }

    private double parseE() {
        double x = parseT();

        while (true) {
            String operator = expressionBuffer.nextToken();

            if (operator.equals("+") || operator.equals("-")) {
                double y = parseT();
                x = math(operator, x, y);
            } else {
                expressionBuffer.back();
                return x;
            }
        }
    }

    private double parseT() {
        double x = parseT2();

        while (true) {
            String operator = expressionBuffer.nextToken();
            if (operator != null && (operator.equals("*") || operator.equals("/"))) {
                double y = parseT2();
                if (operator.equals("/") && y == 0) {
                    System.err.println("You can't divide by zero!");
                    System.exit(-1);
                }
                x = math(operator, x, y);
            } else {
                expressionBuffer.back();
                return x;
            }
        }
    }

    private double parseT2() {
        double x = parseF();
        while (true) {
            String operator = expressionBuffer.nextToken();
            if (operator != null && operator.equals("^")) {
                double y = parseF();
                x = math(operator, x, y);
            } else {
                expressionBuffer.back();
                return x;
            }
        }
    }

    private double parseF() {
        String f = expressionBuffer.nextToken();
        while (true) {
            double a;
            if (isVariable(f)) {
                return vars.get(f);
            } else if (isFunction(f)) {
                double x = parseF();
                return math(f, x);
            } else if (f.equals("(")) {
                a = parseE();
                if (expressionBuffer.nextToken().equals(")")) {
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

    /**
     * This method implements extracting variables and their values from program arguments
     * and putting them to the hashmap;
     */
    public void extractVars(String[] args) {
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
    }


    /**
     * Method implements parsing of the input formula and validating it;
     */
    public void mathExpressionAnalyze(String formula) {
        /* this classes are used for finding matches in the math expression with the following patterns - regexp.
         * Nothing redundant will be saved to the arraylist with the expression - just numbers, variables,
         * operations and functions */
        Pattern pattern = Pattern.compile(functions.buildRegexp() + "|" + "((\\d*\\.?\\d+)|([a-z]+)|\\)|\\(|"
                + operations.buildRegexp() + ")");
        Matcher matcher = pattern.matcher(formula);

        while (matcher.find()) {
            String value = matcher.group();
            if (isVariable(value)) {
                if (!isVarPresent(value, vars)) {
                    System.err.println("There is no value for variable: \"" + value + "\"");
                    System.exit(-1);
                } else {
                    expressionBuffer.add(value);
                }
            } else {
                expressionBuffer.add(value);
            }
        }
        bracketsCheck();
        FirstAndLastTokenCheck();
    }

    /**
     * Method implements check, if the expression starts or ends with an operator.
     */
    private void FirstAndLastTokenCheck() {
        String firstToken = expressionBuffer.getFirst();
        String lastToken = expressionBuffer.getLast();
        if ((isOperator(firstToken) && !firstToken.equals("-")) || isOperator(lastToken)) {
            System.err.println("Expression can't start or end with an operator");
            System.exit(-1);
        }
    }

    /**
     * Method implements:
     * 1) check if the number of opening and closing brackets are the same;
     * 2) check if there is an expression between brackets;
     */
    private void bracketsCheck() {
        int openBracket = 0;
        int closeBracket = 0;
        for (int i = 0; i < expressionBuffer.size(); i++) {
            if (expressionBuffer.get(i).equals("(")) {
                if (expressionBuffer.get(i + 1).equals(")")) {
                    System.err.println("You forgot about expression between brackets. Check and try again");
                    System.exit(-1);
                }
                openBracket += 1;
            }
            if (expressionBuffer.get(i).equals(")")) {
                closeBracket += 1;
            }
        }
        if (openBracket != closeBracket) {
            System.err.println("You forgot about some bracket! Check your expression and try again");
            System.exit(-1);
        }
    }

    /**
     * Method implements check if the expression token is a variable;
     */
    private boolean isVariable(String value) {
        return value.matches("[a-z]+") && !value.matches(functions.buildRegexp());
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
        return operations.map.get(operator).eval(x, y);
    }

    private double math(String operator, double x) {
        return functions.funcMap.get(operator).eval(x);
    }
}
