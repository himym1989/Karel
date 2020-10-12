package com.shpp.p2p.cs.lzhukova.assignment2;


import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * This program describes drawing of two pawprints.
 */

public class Assignment2Part3 extends WindowProgram {

    /* The default width and height of the window. */
    public static final int APPLICATION_WIDTH = 270;
    public static final int APPLICATION_HEIGHT = 220;

    /* Constants controlling the relative positions of the
     * three toes to the upper-left corner of the pawprint.*/
    private static final double FIRST_TOE_OFFSET_X = 0;
    private static final double FIRST_TOE_OFFSET_Y = 20;
    private static final double SECOND_TOE_OFFSET_X = 30;
    private static final double SECOND_TOE_OFFSET_Y = 0;
    private static final double THIRD_TOE_OFFSET_X = 60;
    private static final double THIRD_TOE_OFFSET_Y = 20;

    /* The position of the heel relative to the upper-left
     * corner of the pawprint.
     */
    private static final double HEEL_OFFSET_X = 20;
    private static final double HEEL_OFFSET_Y = 40;

    /* Each toe is an oval with this width and height. */
    private static final double TOE_WIDTH = 20;
    private static final double TOE_HEIGHT = 30;

    /* The heel is an oval with this width and height. */
    private static final double HEEL_WIDTH = 40;
    private static final double HEEL_HEIGHT = 60;

    public void run() {
        drawPawprint(20, 20);
        drawPawprint(180, 70);
    }

    /**
     * Draws a pawprint. The parameters should specify the upper-left corner of the
     * bounding box containing that pawprint.
     *
     * @param x The x coordinate of the upper-left corner of the bounding box for the pawprint.
     * @param y The y coordinate of the upper-left corner of the bounding box for the pawprint.
     */
    private void drawPawprint(double x, double y) {
        Color color = Color.BLACK;
        add(createPawHeel(x, y, color));
        add(createPawToe(x + FIRST_TOE_OFFSET_X, y + FIRST_TOE_OFFSET_Y, color));
        add(createPawToe(x + SECOND_TOE_OFFSET_X, y + SECOND_TOE_OFFSET_Y, color));
        add(createPawToe(x + THIRD_TOE_OFFSET_X, y + THIRD_TOE_OFFSET_Y, color));
    }

    /**
     * @param x     The x coordinate of the upper-left corner of the bounding box for the toes.
     * @param y     The y coordinate of the upper-left corner of the bounding box for the toes.
     * @param color The color to use for the paw toes.
     * @return GOval The paw toe.
     */
    private GOval createPawToe(double x, double y, Color color) {
        GOval toe = new GOval(x, y, TOE_WIDTH, TOE_HEIGHT);
        toe.setColor(color);
        toe.setFillColor(color);
        toe.setFilled(true);
        return toe;
    }

    /**
     * @param x     The x coordinate of the upper-left corner of the bounding box for the paw heel.
     * @param y     The y coordinate of the upper-left corner of the bounding box for the paw heel.
     * @param color The color to use for the paw heel.
     * @return GOval The paw heel.
     */
    private GOval createPawHeel(double x, double y, Color color) {
        GOval heel = new GOval(x + HEEL_OFFSET_X, y + HEEL_OFFSET_Y, HEEL_WIDTH, HEEL_HEIGHT);
        heel.setColor(color);
        heel.setFillColor(color);
        heel.setFilled(true);
        return heel;
    }
}