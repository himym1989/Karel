package com.shpp.p2p.cs.lzhukova.assignment2;


import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;

/**
 * This program describes creating of optical illusion:
 * four black circles in the corners of the application window
 * and one white rectangle, that overlaps parts of the circle
 */
public class Assignment2Part2 extends WindowProgram {

    /* Constants describe width and height of the application window */
    public static final int APPLICATION_WIDTH = 200;
    public static final int APPLICATION_HEIGHT = 100;

    /*  describes diameter of the ovals */
    private int diameter;

    public void run() {
        diameter = getHeight() / 4;
        drawOvals();
        drawRectangle();
    }

    private void drawRectangle() {
        GRect r = new GRect(
                (double) diameter / 2,
                (double) diameter / 2,
                getWidth() - diameter,
                getHeight() - diameter
        );
        r.setColor(Color.white);
        r.setFilled(true);
        r.setFillColor(Color.white);
        add(r);
    }

    /* positioning ovals depending on their positions in the application window */
    private void drawOvals() {
        drawOval(0, 0);
        drawOval(getWidth() - diameter, 0);
        drawOval(0, getHeight() - diameter);
        drawOval(getWidth() - diameter, getHeight() - diameter);
    }

    /* method, that describes drawing an oval */
    private void drawOval(int x, int y) {
        GOval o = new GOval(
                x, y,
                diameter,
                diameter
        );
        o.setColor(Color.black);
        o.setFilled(true);
        o.setFillColor(Color.black);
        add(o);
    }

}
