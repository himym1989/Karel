package com.shpp.p2p.cs.lzhukova.assignment10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This program implements solution of math expressions using shunting-yard algorithm.
 */
public class Assignment10 {
    public static void main(String[] args) {
        System.out.println(args[0]);
        String formula = args[0];
        Calculator calc = new Calculator();
        calc.calculate(formula);
    }
}

class Calculator {
    private static final Map<Character, Integer> operatorPriority = new HashMap<>() {
        {
            put('+', 0); // low
            put('-', 0); // low
            put('*', 1); // high
            put('/', 1); // high
            put('^', 2); // the highest
        }
    };

    // stack will keep math signs of the expression
    private final Stack<Character> Operators = new Stack<>();

    // arraylist will keep numbers and signs as well
    private final ArrayList<String> numAndOperators = new ArrayList<>();

    public void calculate(String arg) {

        sortOperatorsAndNums(arg);
        run();
    }


    private boolean isOperator(String val) {
        return val.matches("[\\-\\+\\*\\^\\/]");
    }

    /**
     * This method implements first stage of shunting-yard algorithm:
     * sorting of numbers and math signs to stack or arraylist.
     * Numbers are added to arraylist in any case. For signs there are some rules:
     * - if stack is empty the first signs is pushed to stack in any case;
     * - if the priority of the previous sign is higher or equal to the priority of the
     * current sign, that we check(look for hashmap "operatorPriority"),
     * the previous sign is popped from the stack and added to arraylist,
     * and the current sign is pushed to the stack.
     *
     * @param arg the math expression.
     */
    private void sortOperatorsAndNums(String arg) {
        Pattern pattern = Pattern.compile("((\\-?\\d*\\.?\\d+)|[\\-\\+\\*\\^\\/])");
        Matcher matcher = pattern.matcher(arg);

        // check if the first number is negative;
        if (arg.charAt(0) == '-') {
            numAndOperators.add("0");
        }
        String previousVal = null;
        boolean isNegative = false;

        while (matcher.find()) {
            String val = matcher.group();
            if (val.equals("-") && isOperator(previousVal)) {
                isNegative = true;
                continue;
            }
            if (isOperator(val)) {
                addOperator(val.charAt(0));
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

    private void run() {
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
        System.out.println("result of expression " + result);

    }


    /**
     * This method implements:
     * - check of signs priority;
     * - signs moves from stack to arraylist, if it is needed.
     */
    private void addOperator(Character operator) {
        int operatorPriority = Calculator.operatorPriority.get(operator);
        int prevOperatorPriority = 3;

        while ((prevOperatorPriority > operatorPriority) && !Operators.empty()) {
            Character prevOperator = Operators.pop();
            prevOperatorPriority = Calculator.operatorPriority.get(prevOperator);
            if (prevOperatorPriority >= operatorPriority) {
                numAndOperators.add(prevOperator.toString());
            } else {
                Operators.push(prevOperator);
            }
        }
        Operators.push(operator);
    }

    private double math(Character operator, double x, double y) {
        switch (operator) {
            case ('+'):
                return x + y;
            case ('-'):
                return x - y;
            case ('*'):
                return x * y;
            case ('/'):
                return x / y;
            case ('^'):
                return Math.pow(x, y);
        }
        throw new IllegalStateException("Unexpected operator: " + operator);
    }
}