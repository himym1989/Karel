package com.shpp.p2p.cs.lzhukova.assignment11;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This program is a calculator, that implements evaluation of numeric expressions using shunting-yard algorithm.
 * It supports such operators as: "-", "+", "/", "*" and "^".
 * It handles negative numbers as well as fractions.
 */

public class Assignment11 {

    /* variables, that will be parsed from the command line*/
    private static HashMap<String, Double> variables = new HashMap();

    public static void main(String[] args) {

        Calculator calc = new Calculator();

        try {
            String formula = args[0];
            variables = extractVariables(args);
            System.out.println(
                    "The result of the numerical expression "
                            + formula + (variables.isEmpty() ? "" : ", where " + variables.entrySet())
                            + " is " + calc.calculate(formula, variables) + ".");
            System.out.println();
        } catch (Exception e) {
            throw e;
//            System.out.println("Illegal math expression");
        }
    }

    /**
     * This method implements parsing variables from the command line and
     * saving them to the hashmap, where key is a variable and value is a number value of the variable.
     */
    private static HashMap<String, Double> extractVariables(String[] args) {
        /* save the value before putting it to the hashmap */
        String buffer;
        String key = "";
        double value = 0;
        Pattern p = Pattern.compile("((\\-?\\d*\\.?\\d+)|[a-zA-Z])");

        for (int i = 1; i < args.length; i++) {

            Matcher m = p.matcher(args[i]);

            while (m.find()) {
                buffer = m.group();
                if (buffer.matches("([a-zA-Z])")) {
                    key = buffer;
                } else if (buffer.matches("(\\-?\\d*\\.?\\d+)")) {
                    value = Double.parseDouble(buffer);
                }
            }
            variables.put(key, value);
        }
        return variables;
    }
}


