package com.shpp.p2p.cs.lzhukova.assignment2;

import com.shpp.cs.a.console.TextProgram;

public class Assignment2Part1 extends TextProgram {

    /**
     * This method describes solutions
     * of the quadratic equation a*(x^2) + b*x + c = 0
     */
    public void run() {

        // read three values from the user
        double a = readInt("Please enter a");
        double b = readInt("Please enter b");
        double c = readInt("Please enter c");

        // calculate the discriminant of the equation
        double D = b * b - 4 * a * c;

        // description of  possible roots calculating
        double firstRoot = (-b + Math.sqrt(D)) / 2 * a;
        double secondRoot = (-b - Math.sqrt(D)) / 2 * a;

        // find the solution depending on the discriminant value
        if (D > 0) {
            println("There are two roots: " + firstRoot + " and " + secondRoot);
        } else if (D == 0) {
            println("There is one root: " + firstRoot);
        } else {
            println("There are no real roots");
        }
    }


}
