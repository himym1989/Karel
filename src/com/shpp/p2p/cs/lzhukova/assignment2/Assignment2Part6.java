package com.shpp.p2p.cs.lzhukova.assignment2;


import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * This program describes drawing of a caterpillar.
 */
public class Assignment2Part6 extends WindowProgram {

    /* Constants describe width and height of the application window */
    public static final int APPLICATION_WIDTH = 150;
    public static final int APPLICATION_HEIGHT = 150;

    /* Constant describes diameter of the circle */
    private static final double CIRCLE_SIZE = APPLICATION_WIDTH / 5.0;

    /* The x- and y-coordinate of the upper-left corner of the bounding box for the caterpillar.*/
    private static final double START_X_POSITION = 0;
    private static final double START_Y_POSITION = 0;

    /* Constant describes number of segments in the caterpillar. */
    private static final double SEGMENT_NUMBER = 6;

    public void run() {
        drawCaterpillar();
    }

    /**
     * Method for drawing of a caterpillar depending on the quantity of segments.
     */
    private void drawCaterpillar() {
        for (int i = 0; i < SEGMENT_NUMBER; i++) {
            add(createCircle(i));
        }
    }

    /**
     * Method for drawing a segment of the caterpillar in a form of a circle.
     *
     * @param index Index of the caterpillar segment.
     * @return GOval Segment of a caterpillar
     */
    private GOval createCircle(int index) {
        /* horizontal shift of the segment depending on the index */
        double xOffset = CIRCLE_SIZE / 2 * index;
        /* vertical shift of the segment, if the index is even */
        double yOffset = index % 2 == 0 ? CIRCLE_SIZE / 3 : 0;
        GOval o = new GOval(
                START_X_POSITION + xOffset,
                START_Y_POSITION + yOffset,
                CIRCLE_SIZE,
                CIRCLE_SIZE
        );
        o.setColor(Color.RED);
        o.setFilled(true);
        o.setFillColor(Color.GREEN);
        return o;
    }
}


