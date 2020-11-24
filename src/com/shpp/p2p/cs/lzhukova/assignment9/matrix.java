package com.shpp.p2p.cs.lzhukova.assignment9;


import acm.graphics.GObject;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;


/**
 * This program describes drawing of optical illusion
 * in the form of a matrix. The matrix consists of black boxes
 * with the spaces between. There are four rows and six cols in the matrix.
 */
public class matrix extends WindowProgram implements ComponentListener {

    /* Constants describe width and height of the application window */
    public static final int APPLICATION_WIDTH = 550;
    public static final int APPLICATION_HEIGHT = 350;

    /* The number of rows and columns in the grid, respectively. */
    private static final int NUM_ROWS = 6;
    private static final int NUM_COLS = 8;

    /* The horizontal and vertical spacing between the boxes. */
    private static final double BOX_SPACING = 10;

    private static final Color MAIN_COLOR = new Color(0, 163, 0);
    private static final double PAUSE = 10000.0 / 24;
    /* x- and y-coordinate of the upper-left corner of the matrix. */
    double offsetX;
    double offsetY;

    int vx = 1;
    int vy = 1;

    int x = 0;
    int y = 0;

    int mouseX = 0;
    int mouseY = 0;

    GRect box;
    GObject mouseBarrier;

    GRect[][] matrix = new GRect[NUM_ROWS][NUM_COLS];

    public void run() {

        addMouseListener(this);
        addComponentListener(this);
        drawMatrix();

        while (true) {
            changeDirection();
            pause(PAUSE);
        }
    }

    private void changeDirection() {
        matrix[y][x].setColor(MAIN_COLOR);

            if (x >= NUM_COLS - 1 || (vx < 0 && x <= 0)) {
                vx *= -1;
            }
        if (y >= NUM_ROWS - 1 || (vy < 0 && y <= 0)) {
            vy *= -1;
        }

        x += vx;
        y += vy;

        matrix[y][x].setColor(Color.blue);
    }


    /**
     * Method describes drawing of the matrix.
     */
    private void drawMatrix() {

        double boxSize = getWidth() / 20f;
        double matrixHeight = NUM_ROWS * boxSize + (NUM_ROWS - 1) * BOX_SPACING;
        double matrixWidth = NUM_COLS * boxSize + (NUM_COLS - 1) * BOX_SPACING;

        /* finding the x-coordinate of the upper-left corner of the matrix. */
        offsetX = (getWidth() - matrixWidth) / 2;
        /* finding the y-coordinate of the upper-left corner of the matrix. */
        offsetY = (getHeight() - matrixHeight) / 2;


        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                box = new GRect(
                        offsetX + (j * (boxSize + BOX_SPACING)),
                        offsetY + (i * (boxSize + BOX_SPACING)),
                        boxSize,
                        boxSize
                );
                box.setColor(MAIN_COLOR);
                box.setFilled(true);
                box.addMouseListener(this);
                add(box);

                matrix[i][j] = box;
            }
        }
    }


    private void update() {
        removeAll();
        drawMatrix();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        mouseBarrier = getElementAt(mouseEvent.getX(), mouseEvent.getY());
        mouseBarrier.setColor(Color.red);
        super.mouseEntered(mouseEvent);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        mouseBarrier.setColor(MAIN_COLOR);
        super.mouseExited(mouseEvent);
    }

    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void componentResized(ComponentEvent e) {
        update();
    }


    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}




