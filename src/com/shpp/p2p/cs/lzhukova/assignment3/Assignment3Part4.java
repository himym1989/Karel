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
    private static final int BRICKS_IN_BASE = 15;
    private static final int BRICKS_SPACING = 5;
    public static final int PYRAMID_WIDTH = (BRICKS_IN_BASE * BRICK_WIDTH) + (BRICKS_SPACING * (BRICKS_IN_BASE - 1));
    private static final int PYRAMID_ROWS = BRICKS_IN_BASE;
    public static final int APPLICATION_WIDTH = PYRAMID_WIDTH * 2;
    public static final int APPLICATION_HEIGHT = BRICKS_IN_BASE * (BRICK_HEIGHT + BRICKS_SPACING);

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
        for (int i = 0; i < BRICKS_IN_BASE - rowNumber; i++) {
            drawBrick(rowNumber, i);
        }
    }

    private void drawBrick(int rowNumber, int brickIndex) {
        double rowOffset = offsetX + rowNumber * (double) (BRICK_WIDTH / 2);
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


