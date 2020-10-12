package com.shpp.p2p.cs.lzhukova.assignment2;

import com.shpp.cs.a.console.TextProgram;


/**
 * This program describes solutions
 * of the quadratic equation a*(x^2) + b*x + c = 0
 * depending on the discriminant value
 */
public class Assignment2Part1 extends TextProgram {

    public void run() {
        // read three values from the user
        double a = readInt("Please enter first value");
        double b = readInt("Please enter second value");
        double c = readInt("Please enter third value");

        // calculate the discriminant of the equation
        double discriminant = b * b - 4 * a * c;

        // description of  possible roots calculating
        double x1 = (-b + Math.sqrt(discriminant)) / 2 * a;
        double x2 = (-b - Math.sqrt(discriminant)) / 2 * a;

        // find the solution depending on the discriminant value
        if (discriminant > 0) {
            println("There are two roots: " + x1 + " and " + x2);
        } else if (discriminant == 0) {
            println("There is one root: " + x1);
        } else {
            println("There are no real roots");
        }
    }
}
