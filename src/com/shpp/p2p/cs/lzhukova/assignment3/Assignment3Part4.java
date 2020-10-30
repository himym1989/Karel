package com.shpp.p2p.cs.lzhukova.assignment3;


import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * This program draws a pyramid of bricks.
 * Each next row of the pyramid contains one brick less,
 * than the previous one.
 */
public class Assignment3Part4 extends WindowProgram {


    /* Height ant width of a brick */
    private static final int BRICK_HEIGHT = 25;
    private static final int BRICK_WIDTH = 50;
    /* amount of bricks in the base */
    private static final int BRICKS_IN_BASE = 3;
    /* Space between the bricks*/
    private static final int BRICKS_SPACING = 5;
    /* calculating the width of the pyramid depending on bricks amount and bricks width*/
    public static final int PYRAMID_WIDTH = (BRICKS_IN_BASE * BRICK_WIDTH) + (BRICKS_SPACING * (BRICKS_IN_BASE - 1));

    /* Application width and height change depending on the pyramid width.
     * In constants must be placed after other parameters of the
     * pyramid and bricks, because depend on them  */
    public static final int APPLICATION_WIDTH = PYRAMID_WIDTH * 2;
    public static final int APPLICATION_HEIGHT = BRICKS_IN_BASE * (BRICK_HEIGHT + BRICKS_SPACING);
    /* The amount of rows depends on the amount of bricks */

    private static final int PYRAMID_ROWS = BRICKS_IN_BASE;
    /* coordinate, that will help to center the pyramid */
    double offsetX;

    public void run() {
        offsetX = (double) getWidth() / 2 - (double) PYRAMID_WIDTH / 2;
        drawPyramid();
    }

    /**
     * Method describes drawing of the pyramid depending on number of the rows.
     */
    private void drawPyramid() {
        for (int i = 0; i < PYRAMID_ROWS; i++) {
            drawPyramidRow(i);
        }
    }

    /**
     * Method describes drawing of the pyramid row depending on amount of bricks.
     *
     * @param rowNumber - number of the row, that defines number of bricks in it
     */
    private void drawPyramidRow(int rowNumber) {
        for (int i = 0; i < BRICKS_IN_BASE - rowNumber; i++) {
            drawBrick(rowNumber, i);
        }
    }

    private void drawBrick(int rowNumber, int brickIndex) {
        // defining an offset in each row depending on the rowNumber.
        double rowOffset = offsetX + rowNumber * (double) (BRICK_WIDTH / 2) + (rowNumber * (double)BRICKS_SPACING / 2);
        GRect brick = new GRect(
                rowOffset + (brickIndex * (BRICK_WIDTH + BRICKS_SPACING)),
                getHeight() - (BRICK_HEIGHT + BRICKS_SPACING) * rowNumber,
                BRICK_WIDTH,
                BRICK_HEIGHT
        );
        brick.setFilled(true);
        brick.setColor(Color.black);
        add(brick);
    }

}


