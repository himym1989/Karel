package com.shpp.p2p.cs.lzhukova.assignment3;

import com.shpp.cs.a.console.TextProgram;

public class Assignment3Part3 extends TextProgram {

    public void run() {
        println(raiseToPower((readDouble("put a number")), readInt("put an exponent")));
    }

    private double raiseToPower(double base, int exponent) {
        double multiplierNum = base;
        double result = base;

        if (exponent == 0) {
            return 1;
        }
        for (int i = 1; i < exponent; i++) {
            if (exponent > 0) {
                //getRegularFraction();
                result = result * multiplierNum;
            }
        }
        return result;
    }

    /*private double getRegularFraction (int numerator, int denominator) {

    }

     */
}
