package com.shpp.p2p.cs.lzhukova.assignment11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Calculator {
    // stack will keep math signs of the expression
    private final Stack<Character> Operators = new Stack<>();

    // arraylist will keep numbers and signs as well
    private final ArrayList<String> numAndOperators = new ArrayList<>();

    MathOperations operations = new MathOperations();

    /**
     * This method implements evaluation of an numeric expression,
     * transforming infix to postfix expression at first.
     */
    public double calculate(String arg, HashMap<String, Double> variables) {
        sortOperatorsAndNums(arg, variables);
        return run();
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
    private void sortOperatorsAndNums(String arg, HashMap<String, Double> variables) {
        Pattern pattern = Pattern.compile("((\\d*\\.?\\d+)|[a-zA-Z]|" + operations.getRegexp() + ")");
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
            if (val.equals("-") && isOperator(previousVal)) {
                isNegative = true;
                continue;
            }

            if (isOperator(val)) {
                addOperator(val.charAt(0));
            } else if (val.matches("[a-zA-Z]")) {
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
        }
        while (!Operators.empty()) {
            numAndOperators.add(Operators.pop().toString());
        }
    }

    /**
     * This method checks if the value is an operator or not.
     */
    private boolean isOperator(String val) {
        return val != null && val.matches(operations.getRegexp());
    }

    /**
     * Method, that starts the calculation.
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
            } else {
                double secondOperand = Double.parseDouble(expression.pop());
                double firstOperand = Double.parseDouble(expression.pop());

                result = math(removedItem.charAt(0), firstOperand, secondOperand);
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
    private void addOperator(Character operator) {
        MathOperation operation = operations.map.get(operator.toString());

        int operatorPriority = operation.getPriority();
        // priority of the previous operator, that is currently at the top of the stack
        int prevOperatorPriority = 3;

        while ((prevOperatorPriority > operatorPriority) && !Operators.empty()) {
            Character prevOperator = Operators.pop();
            MathOperation prevOperation = operations.map.get(prevOperator.toString());
            prevOperatorPriority = prevOperation.getPriority();
            if (prevOperatorPriority >= operatorPriority) {
                numAndOperators.add(prevOperator.toString());
            } else {
                Operators.push(prevOperator);
            }
        }
        Operators.push(operator);
    }

    /**
     * Method implements doing math depending on an operator;
     *
     * @return double, result of math operation;
     */
    private double math(Character operator, double x, double y) {
        if (!operations.map.containsKey(operator.toString())) {
            throw new IllegalStateException("Unexpected operator: " + operator);
        }
        return operations.map.get(operator.toString()).calc(x, y);
    }
}
