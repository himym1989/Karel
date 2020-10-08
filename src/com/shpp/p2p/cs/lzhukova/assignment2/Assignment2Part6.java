package com.shpp.p2p.cs.lzhukova.assignment2;


import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;


public class Assignment2Part6 extends WindowProgram {
    public static final int APPLICATION_WIDTH = 350;
    public static final int APPLICATION_HEIGHT = 350;

    private static final double CIRCLE_SIZE = 70;
    private static final double START_Y_POSITION = 0;
    private static final double START_X_POSITION = 0;
    private static final double SEGMENT_NUMBER = 6;

    public void run() {
        drawCaterpillar();
    }

    private void drawCaterpillar() {
        for (int i = 0; i < SEGMENT_NUMBER; i++) {
            double yOffset = i % 2 == 0 ? CIRCLE_SIZE / 3 : 0;
            add(createOval(i, yOffset));
        }
    }

    private GOval createOval(int i, double yOffset) {
        GOval o = new GOval(
                START_X_POSITION + CIRCLE_SIZE / 2 * i,
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


