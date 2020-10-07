package com.shpp.p2p.cs.lzhukova.assignment2;


import acm.graphics.GOval;
import acm.graphics.GRect;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;


public class Assignment2Part2 extends WindowProgram {
    public static final int APPLICATION_WIDTH = 300;
    public static final int APPLICATION_HEIGHT = 300;
    private static final int DIAMETER = 100;

    public void run() {
        drawOvals();
        drawRectangle();
    }

    private void drawRectangle() {
        GRect r = new GRect(
                (double) DIAMETER / 2,
                (double) DIAMETER / 2,
                getWidth() - DIAMETER,
                getHeight() - DIAMETER
        );
        r.setColor(Color.white);
        r.setFilled(true);
        r.setFillColor(Color.white);
        add(r);
    }

    private void drawOvals() {
        drawOval(0, 0);
        drawOval(getWidth() - DIAMETER, 0);
        drawOval(0, getHeight() - DIAMETER);
        drawOval(getWidth() - DIAMETER, getHeight() - DIAMETER);
    }

    private void drawOval(int x, int y) {
        GOval o = new GOval(
                x, y,
                DIAMETER,
                DIAMETER
        );
        o.setColor(Color.black);
        o.setFilled(true);
        o.setFillColor(Color.black);
        add(o);
    }

}
