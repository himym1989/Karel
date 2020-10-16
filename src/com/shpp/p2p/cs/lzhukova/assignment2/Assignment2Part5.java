package com.shpp.p2p.cs.lzhukova.assignment2;


import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * This program describes drawing of optical illusion
 * in the form of a matrix. The matrix consists of black boxes
 * with the spaces between. There are four rows and six cols in the matrix.
 */
public class Assignment2Part5 extends WindowProgram {

    /* Constants describe width and height of the application window */
    public static final int APPLICATION_WIDTH = 550;
    public static final int APPLICATION_HEIGHT = 350;

    /* The number of rows and columns in the grid, respectively. */
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 6;

    /* The width and height of each box. */
    private static final double BOX_SIZE = 40;

    /* The horizontal and vertical spacing between the boxes. */
    private static final double BOX_SPACING = 10;

    /* Constants describe width and height of the matrix */
    private static final double MATRIX_WIDTH = 290;
    private static final double MATRIX_HEIGHT = 240;

    /* x- and y-coordinate of the upper-left corner of the matrix. */
    double offsetX;
    double offsetY;

    public void run() {
        /* finding the x-coordinate of the upper-left corner of the matrix. */
        offsetX = (getWidth() - MATRIX_WIDTH) / 2;
        /* finding the y-coordinate of the upper-left corner of the matrix. */
        offsetY = (getHeight() - MATRIX_HEIGHT) / 2;
        drawMatrix();
    }

    /**
     * Method describes drawing of the matrix depending on number of the rows.
     */
    private void drawMatrix() {
        for (int i = 0; i < NUM_ROWS; i++) {
            drawMatrixRow(i);
        }
    }

    /**
     * Method describes drawing of the matrix row depending on number of columns.
     */
    private void drawMatrixRow(int rowNumber) {
        for (int i = 0; i < NUM_COLS; i++) {
            drawMatrixItem(rowNumber, i);
        }
    }

    /**
     * Method describes drawing of the matrix item.
     */
    private void drawMatrixItem(int rowNumber, int colNumber) {
        GRect box = new GRect(
                offsetX + (colNumber * (BOX_SIZE + BOX_SPACING)),
                offsetY + (rowNumber * (BOX_SIZE + BOX_SPACING)),
                BOX_SIZE,
                BOX_SIZE
        );
        box.setFilled(true);
        box.setFillColor(Color.BLACK);
        add(box);
    }
}


