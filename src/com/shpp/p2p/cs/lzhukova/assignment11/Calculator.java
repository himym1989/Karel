package com.shpp.p2p.cs.lzhukova.assignment11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Calculator {
    // stack will keep math signs of the expression
    private final Stack<String> operators = new Stack<>();

    // arraylist will keep numbers and signs as well
    private final ArrayList<String> numAndOperators = new ArrayList<>();

    MathOperations operations = new MathOperations();
    MathFunctions functions = new MathFunctions();

    /**
     * This method implements evaluation of an numeric expression,
     * transforming infix to postfix expression at first.
     */
    public double calculate(String arg, HashMap<String, Double> variables) throws Exception {
        sortOperatorsAndNums(arg, variables);
        return run();
    }

    public boolean parenthesisCheck(String arg) {
        int leftParenthesis = 0;
        int rightParenthesis = 0;
        for (int i = 0; i < arg.length(); i++) {
            if (arg.charAt(i) == '(') {
                leftParenthesis += 1;
            }
            if (arg.charAt(i) == ')') {
                rightParenthesis += 1;
            }
        }
        return leftParenthesis == rightParenthesis;
    }


    /**
     * This method implements first stage of shunting-yard algorithm:
     * sorting of numbers and math signs to the stack or to the arraylist.
     * Numbers are added to arraylist in any case. For signs there are some rules:
     * - if stack is empty -  the first signs is pushed to stack in any case;
     * - if the priority of the previous sign in the stack is higher or equal to the priority of the
     * current sign, that we check(look for hashmap "operatorPriority"),
     * the previous sign is popped from the stack and added to arraylist,
     * and the current sign is pushed to the stack.
     *
     * @param arg       the math expression.
     * @param variables hashmap, that keeps variables with their values.
     */
    private void sortOperatorsAndNums(String arg, HashMap<String, Double> variables) throws Exception {
        Pattern pattern = Pattern.compile(functions.buildRegexp() + "|" + "((\\d*\\.?\\d+)|[a-zA-Z]|" + operations.buildRegexp() + ")");
        Matcher matcher = pattern.matcher(arg);

        // check if the first number is negative;
        if (arg.charAt(0) == '-') {
            numAndOperators.add("0");
        }

        // buffer String, tha will keep the value of previous match.
        String previousVal = null;
        // boolean variable, that helps mark if the next number is negative or not
        boolean isNegative = false;

        while (matcher.find()) {
            String val = matcher.group();

            // if there are two operators in a row, and the second is "-"
            // it means, that the next number will be negative;
            if (val.equals("-") && (isOperator(previousVal) && !previousVal.equals(")"))) {
                isNegative = true;
                continue;
            }
            if (isGroupOperator(val) || isOperator(val) || isFunction(val)) {
                addOperator(val);
            } else if (val.matches("[a-zA-Z]")) {
                if (variables.get(val) == null) {
                    throw new Exception("variable `" + val + " has no value");
                }
                numAndOperators.add(variables.get(val).toString());
            } else {
                if (isNegative) {
                    numAndOperators.add("-" + val);
                    isNegative = false;
                } else {
                    numAndOperators.add(val);
                }
            }
            previousVal = val;
            System.out.println(operators);
        }

        while (!operators.empty()) {
            numAndOperators.add(operators.pop());
        }

        // remove remaining parentheses from the arraylist
        numAndOperators.removeIf(o -> o.equals("(") || o.equals(")"));
        System.out.println(numAndOperators);

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
     * Method, that starts the calculation directly.
     *
     * @return double, result of evaluation.
     */
    private double run() {
        Stack<String> expression = new Stack<>();
        double result = 0;

        while (numAndOperators.size() != 0) {
            String removedItem = numAndOperators.remove(0);

            if (removedItem.matches("(\\-?\\d*\\.?\\d+)")) {
                expression.push(removedItem);
            } else if (removedItem.matches(functions.buildRegexp())) {
                double operand = Double.parseDouble(expression.pop());

                result = math(removedItem, operand);
                expression.push(String.valueOf(result));

            } else {
                double secondOperand = Double.parseDouble(expression.pop());
                double firstOperand = Double.parseDouble(expression.pop());

                result = math(removedItem, firstOperand, secondOperand);
                expression.push(String.valueOf(result));
            }
        }
        return result;
    }

    /**
     * This method implements:
     * - check of signs priority;
     * - signs' moves from stack to arraylist, if it is needed.
     */
    private void addOperator(String operator) {
        MathOperation operation = operations.map.get(operator);
        MathFunction function = functions.funcMap.get(operator);

        int operatorPriority;
        // priority of the previous operator, that is currently at the top of the stack
        int prevOperatorPriority;

        // check for parenthesis in an expression and adding all the operators between them
        // to the arraylist
        if (isGroupOperator(operator)) {
            if (!operators.isEmpty() && operator.equals(")")) {
                String prevOperator;
                do {
                    prevOperator = operators.pop();
                    numAndOperators.add(prevOperator);
                }
                while (!operators.isEmpty() && !prevOperator.equals("("));
                numAndOperators.add(operator);
            } else {
                operators.push(operator);

            }
            return;
        }

        // check if "operator" is an operator or a function for getting its priority
        if (isOperator(operator)) {
            operatorPriority = operation.getPriority();
        } else {
            operatorPriority = function.getPriority();
        }

        if (!operators.empty()) {
            do {
                String prevOperator = operators.pop();

                MathOperation prevOperation = operations.map.get(prevOperator);
                MathFunction preVFunction = functions.funcMap.get(prevOperator);

                if (isFunction(prevOperator)) {
                    prevOperatorPriority = preVFunction.getPriority();
                } else {
                    prevOperatorPriority = prevOperation.getPriority();
                }

                if (prevOperatorPriority >= operatorPriority) {
                    numAndOperators.add(prevOperator);
                } else {
                    operators.push(prevOperator);
                }
            }
            while (prevOperatorPriority > operatorPriority && !operators.empty()) ;
        }

        operators.push(operator);
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
        return operations.map.get(operator).calc(x, y);
    }

    private double math(String operator, double x) {
        if (!functions.funcMap.containsKey(operator)) {
            throw new IllegalStateException("Unexpected operator: " + operator);
        }
        return functions.funcMap.get(operator).calc(x);
    }
}
