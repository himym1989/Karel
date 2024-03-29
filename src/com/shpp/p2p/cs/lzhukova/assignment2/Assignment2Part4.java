package com.shpp.p2p.cs.lzhukova.assignment2;


import acm.graphics.GLabel;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * This program describes drawing of vertical tricolor flags.
 */
public class Assignment2Part4 extends WindowProgram {

    /* Constants describe width and height of the application window */
    public static final int APPLICATION_WIDTH = 350;
    public static final int APPLICATION_HEIGHT = 300;

    /* Constants describe additional colors for Belgium flag */
    private static final Color CARDINAL_RED = new Color(196, 30, 58);
    private static final Color YELLOW_FAUX_BANANA = new Color(253, 218, 36);

    /* describing width and height of the flag */
    private double flagWidth;
    private double flagHeight;

    public void run() {
        flagWidth = getWidth() / 1.5;
        flagHeight = getHeight() / 1.5;

        /* finding the x coordinate of the upper-left corner of the flag. */
        double offsetX = (getWidth() - flagWidth) / 2;
        /* finding the y coordinate of the upper-left corner of the flag. */
        double offsetY = (getHeight() - flagHeight) / 2;
        drawVerticalTricolorFlag(offsetX, offsetY);
        writeCountryName();
    }

    /**
     * Method describes writing a country name in the right bottom corner
     */
    private void writeCountryName() {
        GLabel countryName = new GLabel("Flag of Belgium");
        countryName.setFont("Verdana-10");
        countryName.setColor(Color.BLACK);
        double x = getWidth() - countryName.getWidth();
        double y = getHeight() - countryName.getHeight();
        add(countryName, x, y);
    }

    /**
     * Method describes drawing vertical tricolor flag, in particular positions of each color
     */
    private void drawVerticalTricolorFlag(double x, double y) {
        add(createColumn(x, y, Color.BLACK));
        add(createColumn(x + flagWidth / 3, y, YELLOW_FAUX_BANANA));
        add(createColumn(x + flagWidth / 3 * 2, y, CARDINAL_RED));
    }

    /**
     * Method describes creating of color columns in the flag.
     */
    private GRect createColumn(double x, double y, Color color) {
        GRect column = new GRect(x, y, flagWidth / 3, flagHeight);
        column.setColor(color);
        column.setFillColor(color);
        column.setFilled(true);
        return column;
    }
}
