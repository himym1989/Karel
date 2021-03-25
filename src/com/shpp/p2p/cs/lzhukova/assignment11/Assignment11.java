package com.shpp.p2p.cs.lzhukova.assignment11;


import java.util.HashMap;


/**
 * This program implements calculator, that supports the following operators and functions:
 * "-", "+", "/", "*" , "^";
 * sin, cos, tan, atan, log10, log2, sqrt;
 */

public class Assignment11 {

    /* hashmap, that will contain variables with their values */
    private static HashMap<String, Double> vars = new HashMap<>();


    public static void main(String[] args) throws Exception {
        checkArguments(args);

        Calculator calculator = new Calculator();
        String mathExpression = args[0].toLowerCase().replaceAll(" ", "");
        vars = extractVars(args);

        System.out.println("The result of math expression " + mathExpression +
                (vars.isEmpty() ? "" : ", where variables are " + vars) + " is...." +
                calculator.calculate(mathExpression, vars));
    }

    private static void checkArguments(String[] args) throws Exception {
        if (args.length == 0) {
            throw new Exception("You haven't input any expression");
        }
    }


    /**
     * This method implements extracting variables and their values from program arguments
     * and putting them to the hashmap;
     *
     * @param args - String array from the command line;
     * @return vars, Hashmap with vars and values;
     */
    private static HashMap<String, Double> extractVars(String[] args) throws Exception {
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
}
