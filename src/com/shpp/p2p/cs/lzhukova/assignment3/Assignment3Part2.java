package com.shpp.p2p.cs.lzhukova.assignment3;

import com.shpp.cs.a.console.TextProgram;


/**
 * This program describes the fun math game - Hailstone Numbers.
 * Start with any positive integer n.  If the current number is even,
 * divide it by two; else if it is odd,
 * multiply it by three and add one.
 * Repeat, while number will become 1.
 */
public class Assignment3Part2 extends TextProgram {

    public void run() {
        int n = readDataFromUser();
        printHailstoneNumbers(n);
    }

    /**
     * This method describes getting data from the user
     * and check, if the number is positive.
     *
     * @return n - positive integer, that the user inputs.
     */
    private int readDataFromUser() {
        int n = readInt("put a positive integer: ");
        while (n <= 0) {
            println("This number is not positive.");
            n = readInt("put a positive integer: ");
        }
        return n;
    }

    /**
     * Method describes mathematical actions depending on the fact,
     * is a number odd or even.
     *
     * @param n - number, that changes in the loop.
     *          The loop stops when n==1;
     */
    private void printHailstoneNumbers(int n) {
        while (n != 1) {
            if (n % 2 == 0) {
                println(n + " is even so I take half: " + n / 2);
                n = n / 2;
            } else {
                println(n + " is odd so I make 3n + 1: " + ((n * 3) + 1));
                n = (n * 3) + 1;
            }
        }
    }

}
