package com.shpp.p2p.cs.lzhukova.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.util.ArrayList;

public class Assignment5Part2 extends TextProgram {
    public void run() {
        /* Sit in a loop, reading numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first number:  ");
            String n2 = readLine("Enter second number: ");
            println(n1 + " + " + n2 + " = " + addNumericStrings(n1, n2));
            println();
        }
    }

    /**
     * Given two string representations of nonnegative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */

    private String addNumericStrings(String n1, String n2) {
        StringBuilder resultOfAdding = new StringBuilder();

        int i = n1.length() - 1;
        int j = n2.length() - 1;
        int rest = 0;

        while (i >= 0 || j >= 0) {
            int num1 = i >= 0 ? n1.charAt(i) - '0' : 0;
            int num2 = j >= 0 ? n2.charAt(j) - '0' : 0;

            int addingUp = num1 + num2 + rest;
            if (addingUp >= 10) {
                addingUp -= 10;
                rest = 1;
            } else {
                rest = 0;
            }

            resultOfAdding.insert(0, addingUp);

            i--;
            j--;
        }

        if (rest == 1) {
            resultOfAdding.insert(0, rest);
        }

        return resultOfAdding.toString();
    }

}