package com.shpp.p2p.cs.lzhukova.train;

import acm.graphics.GObject;
import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class MidTest extends WindowProgram implements ComponentListener {


    /* Constants describe width and height of the application window */
    public static final int APPLICATION_WIDTH = 550;
    public static final int APPLICATION_HEIGHT = 350;

    private static final Color YELLOW_FAUX_BANANA = new Color(253, 218, 36);

    private static final double PAUSE = 1000.0 / 24;

    private static final int circleAmount = 7;

    private GOval circle;

    /* variables, that control move direction and velocity of the ball */
    private double vx = 5;
    private double vy = 5;

    private boolean stop;

    /**
     * This hashmap keeps states of circles:
     * "s" - stopped, default state;
     * "h" - moves horizontally;
     * "v" - moves vertically;
     */
    private HashMap<GObject, String> circles = new HashMap<>();


    @Override
    public void run() {
        addComponentListener(this);
        drawCircles();
        startAnimation();
    }

    private void startAnimation() {
        while (true) {
            if (stop) break;

            if (!circles.isEmpty()) {
                for (Map.Entry<GObject, String> entry : circles.entrySet()) {
                    String value = entry.getValue();

                    if (value.equals("h")) {
                        moveHorizontally((GOval) entry.getKey());
                    } else if (value.equals("v")) {
                        moveVertically((GOval) entry.getKey());
                    }
                }
            }

            pause(PAUSE);
        }
    }

    private void moveHorizontally(GOval circle) {
        checkWalls();
        circle.move(vx, 0);
    }

    private void moveVertically(GOval circle) {
        checkWalls();
        circle.move(0, vy);
    }


    private void drawCircles() {
        /* size of the circles */
        int size = getHeight() > getWidth() ? getWidth() / circleAmount : getHeight() / circleAmount;
        /* interval between the circles */
        int interval = getHeight() > getWidth() ? (getHeight() - circleAmount * size) / circleAmount :
                (getWidth() - circleAmount * size) / circleAmount;

        for (int i = 0; i < circleAmount; i++) {
            circle = new GOval(getHeight() < getWidth() ? i * (size + interval) : i * size,
                    getHeight() > getWidth() ? i * (size + interval) : i * size,
                    size,
                    size);
            circle.setColor(YELLOW_FAUX_BANANA);
            circle.setFilled(true);
            add(circle);

            circles.put(circle, "s");
            circle.addMouseListener(this);
        }
    }

    private void checkWalls() {
        if (circle.getX() <= 0 || (circle.getX() >= getWidth() - circle.getWidth())) {
            vx = -vx;
        }
        if (circle.getY() <= 0) {
            vy = -vy;
        }
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        GOval selectedObject = (GOval) getElementAt(mouseEvent.getX(), mouseEvent.getY());
        selectedObject.setColor(Color.blue);

        System.out.println("a");

        // we check availability of the object
        // we search same object in hashMap
        for (Map.Entry<GObject, String> entry : circles.entrySet()) {

            if (selectedObject == entry.getKey()) {

                switch (entry.getValue()) {
                    case "h" -> entry.setValue("v");
                    case "v" -> entry.setValue("s");
                    case "s" -> entry.setValue("h");
                }
            }
        }
        //        moveHorizontally(selectedObject);

        super.mouseClicked(mouseEvent);

    }

    @Override
    public void componentResized(ComponentEvent e) {
        update(e);
    }

    private void update(ComponentEvent e) {
        removeAll();
        drawCircles();
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
