package com.shpp.p2p.cs.lzhukova.assignment3;


import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 *
 */
public class Assignment3Part4 extends WindowProgram {



    private static final int BRICK_HEIGHT = 20;
    private static final int BRICK_WIDTH = 50;

    private static final int BRICKS_IN_BASE = 10;
    private static final int BRICKS_SPACING = 5;

    private static final int PYRAMID_ROWS = BRICKS_IN_BASE;

    public static final int PYRAMID_WIDTH = (BRICKS_IN_BASE * BRICK_WIDTH) + (BRICKS_SPACING * (BRICKS_IN_BASE - 1));
    public static final double APPLICATION_WIDTH = PYRAMID_WIDTH*1.2;
    public static final int APPLICATION_HEIGHT = 500;
    double offsetX;


    public void run() {
        offsetX = (double) (getWidth() - PYRAMID_WIDTH) / 2;

        drawPyramid();
    }

    private void drawPyramid() {
        for (int i = 0; i < PYRAMID_ROWS; i++) {
            drawPyramidRow(i);
        }
    }

    private void drawPyramidRow(int rowNumber) {
        for (int i = 1; i <= BRICKS_IN_BASE-rowNumber; i++) {
            drawBrick(rowNumber, i);
        }
    }


    private void drawBrick(int rowNumber, int brickIndex) {
        println(brickIndex * (BRICKS_SPACING + BRICK_WIDTH));
        GRect brick = new GRect(
                offsetX + brickIndex * (BRICKS_SPACING + BRICK_WIDTH),
                getHeight() - (BRICK_HEIGHT + BRICKS_SPACING) * rowNumber,
                BRICK_WIDTH,
                BRICK_HEIGHT
        );
        brick.setFilled(true);
        brick.setColor(Color.black);
        add(brick);
    }

}


