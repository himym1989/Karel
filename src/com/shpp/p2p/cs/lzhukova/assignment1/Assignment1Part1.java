package com.shpp.p2p.cs.lzhukova.assignment1;


public class Assignment1Part1 extends Assignment1Superclass {

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
}
