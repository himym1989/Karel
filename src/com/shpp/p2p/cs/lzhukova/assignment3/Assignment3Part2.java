package com.shpp.p2p.cs.lzhukova.assignment3;

import com.shpp.cs.a.console.TextProgram;

public class Assignment3Part2 extends TextProgram {

    public void run() {
        int n = readInt("put a positive integer");

        while (n > 1) {
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
