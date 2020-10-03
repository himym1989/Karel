package com.shpp.p2p.cs.lzhukova.assignment1;

import com.shpp.karel.KarelTheRobot;


public class Assignment1Part1 extends KarelTheRobot {

    /**
     * Precondition: Karel stays at the Start Position.
     * Result: Karel picked the beeper and
     * went back to the Start Position
     */
    public void run() throws Exception {
        goToBeeper();
        pickBeeper();
        goBackToStartPosition();
    }

    /**
     * Precondition: Karel put the beeper to his bag and stays on its cell
     * Result: Karel returned to his Start Position.
     */
    private void goBackToStartPosition() throws Exception {
        turnAround();
        while (frontIsClear()) {
            move();
        }
        turnRight();
        move();
    }

    /**
     * No precondition.
     * Karel just wants to turn 180 degrees
     * regarding his previous position.
     */
    private void turnAround() throws Exception {
        turnLeft();
        turnLeft();
    }

    /**
     * Precondition: Karel stays at the Start Position, faces the East.
     * He turns right and move to the next row
     * to avoid the wall on his path.
     * Result: Karel put a beeper to his bag.
     */
    private void goToBeeper() throws Exception {
        turnRight();
        move();
        turnLeft();
        while (noBeepersPresent()) {
            move();
        }
    }

    /**
     * No precondition.
     * Karel just wants to turn right
     * in regard to his previous position
     */
    private void turnRight() throws Exception {
        for (int i = 0; i < 3; i++) {
            turnLeft();
        }
    }
}
