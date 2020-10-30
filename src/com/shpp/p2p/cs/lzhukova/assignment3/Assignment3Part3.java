package com.shpp.p2p.cs.lzhukova.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * This program reads two parameters from the user
 * and raises first parameter to power of the second.
 */
public class Assignment3Part3 extends TextProgram {
    // first parameter, double, that will be raised to power
    private double base;
    // second parameter, exponent
    private int exponent;

    public void run() {
        base = readDouble("put a number: ");
        exponent = readInt("put an exponent: ");

        println(raiseToPower());
    }

    /**
     * This method performs different exponentiation actions
     * depending on the fact, if the numbers positive,
     * negative or equals zero.
     * @return result of thе exponentiation.
     */
    private double raiseToPower() {
        // A negative exponent means how many times to divide by the number.
        // To change the sign ( minus to plus) of the exponent,
        // use the Reciprocal (1/base^exponent)
        double x = (exponent < 0) ? (1 / base) : base;
        int y = exponent < 0 ? -exponent : exponent;
        double result = x;

        if (exponent == 0) {
            return 1;
        }
        for (int i = 1; i < y; i++) {
            result = result * x;
        }
        return result;
    }

}
