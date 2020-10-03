package com.shpp.p2p.cs.lzhukova.assignment1;

import com.shpp.karel.KarelTheRobot;


public class Assignment1Part2 extends KarelTheRobot {


    /**
     * Precondition: Karel stays in the South-West corner,
     * he looks East.
     * Result: Karel walked along all of columns
     * and put beepers in free cells.
     */
    public void run() throws Exception {
        // Karel fills in the first column for sure, if the world consists only of 1 column.
        fillTheColumn();
        // Karel fills in the rest of columns, if there are more than 1 column in the world
        while (frontIsClear()) {
            moveToTheNextColumn();
            fillTheColumn();
        }
    }

    /**
     * Precondition: Karel stays at the bottom of the column, looks East.
     * Result: Karel came back to the bottom of the filled column.
     * He turned East and is ready to move to the next column.
     */
    private void fillTheColumn() throws Exception {
        turnLeft();
        if (noBeepersPresent()) {
            putBeeper();
        }
        while (frontIsClear()) {
            move();
            if (noBeepersPresent()) {
                putBeeper();
            }
        }
        goBackToTheBottomOfColumn();
    }

    /**
     * Precondition: Karel stays at the bottom of the filled column,
     * he faces East.
     * Result: Karel stays at the bottom of the next column to fill,
     * he faces East
     */
    private void moveToTheNextColumn() throws Exception {
        for (int i = 0; i < 4; i++) {
            move();
        }
    }

    /**
     * Precondition: Karel stays at the top of the filled column.
     * Result: Karel came down to the bottom of the filled column
     * and turned East
     */
    private void goBackToTheBottomOfColumn() throws Exception {
        turnAround();
        while (frontIsClear()) {
            move();
        }
        turnLeft();
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
}
