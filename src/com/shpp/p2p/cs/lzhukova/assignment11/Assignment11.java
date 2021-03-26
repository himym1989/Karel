package com.shpp.p2p.cs.lzhukova.assignment11;


import java.util.HashMap;
import java.util.Scanner;


/**
 * This program implements calculator, that supports the following operators and functions:
 * "-", "+", "/", "*" , "^";
 * sin, cos, tan, atan, log10, log2, sqrt;
 */

public class Assignment11 {

    public static void main(String[] args) {
        checkArguments(args);

        Calculator calculator = new Calculator();
        String mathExpression = args[0].toLowerCase().replaceAll(" ", "");
        double result = calculator.calculate(mathExpression, args);

        System.out.println("The result of math expression " + mathExpression +
                (calculator.vars.isEmpty() ? "" : " with the following variables' value " + calculator.vars) + " is " +
                result);


        Scanner in = new Scanner(System.in);
        System.out.println("Would you like to solve the expression with other variables value?... Y/N");
        String answer = in.nextLine().toLowerCase();
        if (answer.equals("y")) {
            for (String key : calculator.vars.keySet()) {
                System.out.println("Enter a value for variable " + key);
            }
        } else if (answer.equals("n")) {
            System.out.println("bye!");
        }
    }

    private static void checkArguments(String[] args) {
        if (args.length == 0 || args[0].equals("")) {
            System.err.println("You haven't input any expression");
            System.exit(-1);
        }
    }
}
