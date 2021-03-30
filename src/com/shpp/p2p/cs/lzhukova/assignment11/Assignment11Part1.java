package com.shpp.p2p.cs.lzhukova.assignment11;


/**
 * This program implements calculator, that supports the following operators and functions:
 * "-", "+", "/", "*" , "^";
 * sin, cos, tan, atan, log10, log2, sqrt;
 */

public class Assignment11Part1 {

    public static void main(String[] args) {
        checkArguments(args);
        Calculator calculator = new Calculator(args);
        double result = calculator.calculate();

        System.out.println(
                "The result of math expression " + args[0].replaceAll(" ", "") +
                        (calculator.vars.isEmpty() ? "" : " with the following variables' value " + calculator.vars)
                        + " is " + result
        );


//        Scanner in = new Scanner(System.in);
//        System.out.println("Would you like to solve the expression with other variables values?... Y/N");
//        String answer = in.nextLine().toLowerCase();
//        if (answer.equals("y")) {
//            for (String key : expressionParser.vars.keySet()) {
//                System.out.println("Enter a value for variable " + key);
//                expressionParser.vars.put(key, in.nextDouble());
//            }
//            System.out.println("The result of math expression " + formula +
//                    (expressionParser.vars.isEmpty() ? "" : " with the following variables' value " + expressionParser.vars) + " is " +
//                    calculator.calculate(expressionParser.mathExpressionAnalyze(), expressionParser.vars));
//        } else if (answer.equals("n")) {
//            System.out.println("bye!");
//        }
    }

    private static void checkArguments(String[] args) {
        if (args.length == 0 || args[0].equals("")) {
            System.err.println("You haven't input any expression");
            System.exit(-1);
        }
    }
}
