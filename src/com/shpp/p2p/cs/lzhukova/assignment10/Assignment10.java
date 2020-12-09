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
        Calculator calc = new Calculator();
        calc.calculate(args[0]);
    }
}
//  java -cp src com/shpp/p2p/cs/lzhukova/assignment10/Assignment10 1+2

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
    private final Stack<Character> mathSigns = new Stack<>();

    // arraylist will keep numbers and signs as well
    private final ArrayList<String> numAndSigns = new ArrayList<>();

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
    private void sortSignsAndNums(String arg) {
        Pattern pattern = Pattern.compile("([0-9]+|[\\-\\+\\*\\^\\/])");
        Matcher matcher = pattern.matcher(arg);
        while (matcher.find()) {
            String val = matcher.group();
            boolean isSign = val.matches("[\\-\\+\\*\\^\\/]");

            if (isSign) {
                addSign(val.charAt(0));
            } else {
                numAndSigns.add(val);
            }
        }
        while (!mathSigns.empty()) {
            numAndSigns.add(mathSigns.pop().toString());
        }
    }

    /**
     * This method implements:
     * - check of signs priority;
     * - signs moves from stack to arraylist, if it is needed.
     */
    private void addSign(Character sign) {
        int signPriority = operatorPriority.get(sign);
        int prevSignPriority = 3;

        while ((prevSignPriority > signPriority) && !mathSigns.empty()) {
            Character prevSign = mathSigns.pop();
            prevSignPriority = operatorPriority.get(prevSign);
            if (prevSignPriority >= signPriority) {
                numAndSigns.add(prevSign.toString());
            } else {
                mathSigns.push(prevSign);
            }
        }
        mathSigns.push(sign);
    }

    public void calculate(String arg) {
        mathSigns.clear();
        numAndSigns.clear();
        sortSignsAndNums(arg);
        run();
    }

    private void run() {
        Stack<String> expression = new Stack<>();
        int result = 0;
        System.out.println(numAndSigns);

        while (numAndSigns.size() != 0) {
            String removedItem = numAndSigns.remove(0);

            if (removedItem.matches("[0-9]+")) {
                expression.push(removedItem);
            } else {
                int secondOperand = Integer.parseInt(expression.pop());
                int firstOperand = Integer.parseInt(expression.pop());
                result = math(removedItem.charAt(0), firstOperand, secondOperand);
                expression.push(String.valueOf(result));
                System.out.println(result);
            }
        }
        System.out.println("res " + result);

//        System.out.println(numAndSigns);
//        System.out.println(expression);
    }

    private int math(Character sign, int x, int y) {
        switch (sign) {
            case ('+'):
                return x + y;
            case ('-'):
                return x - y;
            case ('*'):
                return x * y;
            case ('/'):
                return x / y;
            case ('^'):
                return (int) Math.pow(x, y);
        }
        throw new IllegalStateException("Unexpected sign: " + sign);
    }
}