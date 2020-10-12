package com.shpp.p2p.cs.lzhukova.assignment2;


import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * This program describes drawing of a caterpillar.
 */
public class Assignment2Part6 extends WindowProgram {

    /* Constants describe width and height of the application window */
    public static final int APPLICATION_WIDTH = 350;
    public static final int APPLICATION_HEIGHT = 300;

    /* The x- and y-coordinate of the upper-left corner of the bounding box for the caterpillar. */
    private static final double START_X_POSITION = 0;
    private static final double START_Y_POSITION = 0;

    /* Constant describes number of segments in the caterpillar. */
    private static final double SEGMENT_NUMBER = 6;

    /* Size of a caterpillar segment */
    private double circleSize;

    public void run() {
        circleSize = getWidth() / 4.0;
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
        double xOffset = circleSize / 2 * index;
        /* vertical shift of the segment, if the index is even */
        double yOffset = index % 2 == 0 ? circleSize / 3 : 0;
        GOval o = new GOval(
                START_X_POSITION + xOffset,
                START_Y_POSITION + yOffset,
                circleSize,
                circleSize
        );
        o.setColor(Color.RED);
        o.setFilled(true);
        o.setFillColor(Color.GREEN);
        return o;
    }
}


