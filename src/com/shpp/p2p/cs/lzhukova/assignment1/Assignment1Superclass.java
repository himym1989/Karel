package com.shpp.p2p.cs.lzhukova.assignment1;

import com.shpp.karel.KarelTheRobot;

public class Assignment1Superclass extends KarelTheRobot {
    /**
     * No precondition.
     * Karel just wants to turn 180 degrees
     * regarding his previous position.
     */
    protected void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }

    /**
     * No precondition.
     * Karel just wants to turn right
     * in regard to his previous position
     */
    protected void turnRight() throws Exception {
        for (int i = 0; i < 3; i++) {
            turnLeft();
        }
    }
}
