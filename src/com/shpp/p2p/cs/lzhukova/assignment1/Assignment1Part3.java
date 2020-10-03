package com.shpp.p2p.cs.lzhukova.assignment1;

import com.shpp.karel.KarelTheRobot;


public class Assignment1Part3 extends KarelTheRobot {

    /**
     * Precondition: Karel stays at the South-West corner, he looks East.
     * Result: Karel marked the center of the South line with a beeper.
     */
    public void run() throws Exception {
        putCheckupBeepers();
        shiftBeepersToCenter();
        pickExtraBeeper();
    }

    /**
     * Precondition: Karel stays at the South-West corner, he looks East.
     * Result: Karel put two beepers, that later will help to find the center:
     * one - in the South-West corner, second -in the South-East corner
     */
    private void putCheckupBeepers() throws Exception {
        putBeeper();
        while (frontIsClear()) {
            move();
        }
        putBeeper();
    }

    /**
     * Precondition: Karel stays at the South-East corner.
     * Result: Karel shifted beepers to the center, one beeper is extra and
     * must be picked later
     */
    private void shiftBeepersToCenter() throws Exception {
        turnAround();
        while (frontIsClear()) {
            move();
            if (beepersPresent()) {
                pickBeeper();
                turnAround();
                move();
                putBeeper();
                move();
            }
        }
    }

    /**
     * There are two scenarios:
     * 1) If number of cells is odd, Karel starts from South-East corner,
     * moves towards the center and picks extra beeper,
     * the beeper in the center lefts;
     * 2) If number of cells is even, Karel starts from South-West corner,
     * moves towards the center and picks one of the beepers
     * in the center cells. The other beeper is left.
     */

    private void pickExtraBeeper() throws Exception {
        turnAround();
        while (noBeepersPresent()) {
            move();
        }
        pickBeeper();
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
