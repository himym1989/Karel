package com.shpp.p2p.cs.lzhukova.assignment1;


public class Assignment1Part4 extends Assignment1Superclass {

    /**
     * Precondition: Karl stays in South-West corner,
     * he looks East.
     * Postcondition: Karel filled the world with beepers
     * as a checkerboard: one cell is filled, another is empty
     * and so on. The first beeper Karel lefts in the South-West corner.
     */
    public void run() throws Exception {
        checkOneCellWorld();
        while (frontIsClear()) {
            fillOddRowAndReturnBack();
            fillEvenRowAndReturnBack();
        }
    }

    /**
     * Precondition: Karel stays in South-West corner,looks East.
     * He checks, if the world has one-cell width
     * and one-cell height. Karel puts beeper,
     * if the world consists from one cell.
     */
    private void checkOneCellWorld() throws Exception {
        if (frontIsBlocked()) {
            turnLeft();
            if (frontIsBlocked()) {
                putBeeper();
            }
        }
    }

    /**
     * This method refers the filling of even rows.
     * The first cell will be always not filled.
     */
    private void fillEvenRowAndReturnBack() throws Exception {
        while (frontIsClear()) {
            skipTheCell();
            fillTheCell();
        }
        checkNextRowAndSwitch();
    }


    /**
     * This method refers the filling of odd rows.
     * The first cell will be always filled with a beeper.
     */
    private void fillOddRowAndReturnBack() throws Exception {
        fillTheCell();
        while (frontIsClear()) {
            skipTheCell();
            fillTheCell();
        }
        checkNextRowAndSwitch();
    }

    /**
     * Karel checks the opportunity of going to the next row.
     */
    private void checkNextRowAndSwitch() throws Exception {
        if (leftIsClear()) {
            returnBack();
            changeTheRow();
        }
    }

    /**
     * Precondition: Karl stays at the beginning
     * of the filled row, looks West.
     * Postcondition: Karel stays at the beginning
     * of the next row, looks East.
     */
    private void changeTheRow() throws Exception {
        turnRight();
        move();
        turnRight();
    }

    /**
     * Precondition: Karel stays in the East of a filled row.
     * Postcondition: Karel returned back to the beginning of the row.
     */
    private void returnBack() throws Exception {
        turnAround();
        while (frontIsClear()) {
            move();
        }
    }

    /**
     * No precondition.
     * Karel just skips the cell.
     */
    private void skipTheCell() throws Exception {
        if (frontIsClear()) {
            move();
        }
    }

    /**
     * No precondition.
     * Karel puts beeper in a cell and moves.
     */
    private void fillTheCell() throws Exception {
        putBeeper();
        if (frontIsClear()) {
            move();
        }
    }

}
