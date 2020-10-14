package com.shpp.p2p.cs.lzhukova.assignment3;

import com.shpp.cs.a.console.TextProgram;

public class Assignment3Part3 extends TextProgram {

    double base;
    int exponent;

    public void run() {
        base = readDouble("put a number");
        exponent = readInt("put an exponent");

        println(raiseToPower(base, exponent));
    }

    private double raiseToPower(double x, int y) {
        x = (exponent < 0) ? (1 / base) : base;
        y = exponent < 0 ? -exponent : exponent;
        double result = x;

        if (exponent == 0) {
            return 1;
        }

        for (int i = 1; i < y; i++) {
            //getRegularFraction();
            result = result * x;
        }
        return result;
    }

}
