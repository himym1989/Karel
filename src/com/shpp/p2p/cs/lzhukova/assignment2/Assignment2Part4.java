package com.shpp.p2p.cs.lzhukova.assignment2;


import acm.graphics.GLabel;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;


public class Assignment2Part4 extends WindowProgram {
    public static final int APPLICATION_WIDTH = 350;
    public static final int APPLICATION_HEIGHT = 300;
    public static final double FLAG_WIDTH = 200;
    public static final double FLAG_HEIGHT = 180;

    private static final Color CARDINAL_RED = new Color(196, 30, 58);
    private static final Color YELLOW_FAUX_BANANA = new Color(253, 218, 36);

    private String country;

    public void run() {
        country = "Belgium";
        double offsetX = (getWidth() - FLAG_WIDTH) / 2;
        double offsetY = (getHeight() - FLAG_HEIGHT) / 2;
        drawVerticalTricolorFlag(offsetX, offsetY);
        writeCountryName();
    }

    private void writeCountryName() {
        GLabel countryName = new GLabel("Flag of " + country);
        countryName.setFont("Verdana-10");
        countryName.setColor(Color.BLACK);
        double x = getWidth() - countryName.getWidth();
        double y = getHeight() - countryName.getHeight();
        add(countryName, x, y);
    }

    private void drawVerticalTricolorFlag(double x, double y) {
        add(createColumn(x, y, Color.BLACK));
        add(createColumn(x + FLAG_WIDTH / 3, y, YELLOW_FAUX_BANANA));
        add(createColumn(x + FLAG_WIDTH / 3 * 2, y, CARDINAL_RED));
    }

    private GRect createColumn(double x, double y, Color color) {
        GRect column = new GRect(x, y, FLAG_WIDTH / 3, FLAG_HEIGHT);
        column.setColor(color);
        column.setFillColor(color);
        column.setFilled(true);
        return column;
    }
}
