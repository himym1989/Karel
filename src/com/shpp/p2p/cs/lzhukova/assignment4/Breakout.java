package com.shpp.p2p.cs.lzhukova.assignment4;

import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Breakout extends WindowProgram {
    /**
     * Width and height of application window in pixels
     */
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * Dimensions of game board (usually the same)
     */
    private static final int WIDTH = APPLICATION_WIDTH;
    private static final int HEIGHT = APPLICATION_HEIGHT;

    private static final double PAUSE = 1000.0 / 48;


    /**
     * Dimensions of the paddle
     */
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;

    /**
     * Offset of the paddle up from the bottom
     */
    private static final int PADDLE_Y_OFFSET = 30;

    /**
     * Number of bricks per row
     */
    private static final int NBRICKS_PER_ROW = 10;

    /**
     * Number of rows of bricks
     */
    private static final int NBRICK_ROWS = 10;

    /**
     * Separation between bricks
     */
    private static final int BRICK_SEP = 4;

    /**
     * Width of a brick
     */
    private static final int BRICK_WIDTH =
            (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

    /**
     * Height of a brick
     */
    private static final int BRICK_HEIGHT = 8;

    /**
     * Radius of the ball in pixels
     */
    private static final int BALL_RADIUS = 10;

    /**
     * Offset of the top brick row from the top
     */
    private static final int BRICK_Y_OFFSET = 70;

    private static final double BALL_ACCELERATION = 1.05;
    private static final int BRICKS_AMOUNT = NBRICK_ROWS * NBRICKS_PER_ROW;


    /**
     * Number of turns
     */
    private static final int NTURNS = 3;

    private GRect paddle;
    private GOval ball;
    private double vx;
    private double vy;

    private int turns = NTURNS;

    public void run() {
        addBricks();
        addPaddle();
        addBall();

        addMouseListeners();

        while (turns > 0) {
            println("start: " + turns);
            centerBall();
            waitForClick();
            playGame();
        }

        // TODO: show message
    }

    private void addBricks() {
        double xOffset = (getWidth() - (BRICK_WIDTH * NBRICKS_PER_ROW + BRICK_SEP * (NBRICKS_PER_ROW - 1))) / 2;

        for (int row = 0; row < NBRICK_ROWS; row++) {
            for (int col = 0; col < NBRICKS_PER_ROW; col++) {

                GRect brick = new GRect(
                        xOffset + col * (BRICK_WIDTH + BRICK_SEP),
                        BRICK_Y_OFFSET + (row * (BRICK_HEIGHT + BRICK_SEP)),
                        BRICK_WIDTH,
                        BRICK_HEIGHT
                );
                brick.setColor(getBrickColor(row));
                brick.setFilled(true);
                add(brick);
            }
        }
    }

    /**
     * Change of the bricks color depending on the number of row.
     *
     * @param row - index number of the row.
     * @return - color.
     */
    private Color getBrickColor(int row) {
        if (row < 2) return Color.RED;
        if (row == 2 || row == 3) return Color.ORANGE;
        if (row == 4 || row == 5) return Color.YELLOW;
        if (row == 6 || row == 7) return Color.GREEN;
        return Color.CYAN;
    }


    /**
     * Method implements the break-out game,
     * handles collision with objects
     */
    private void playGame() {
        RandomGenerator rgen = RandomGenerator.getInstance();
        vx = rgen.nextDouble(1.0, 5.0);
        if (rgen.nextBoolean(0.5)) {
            vx = -vx;
        }
        vy = 7;

        while (true) {
            ball.move(vx, vy);
            checkWallsChangeDirection();
            GObject collider = getCollidingObject();
            if (collider == paddle) {
                vy = -vy;
            } else if (collider != null) {
                vy = -(vy * BALL_ACCELERATION);
                remove(collider);
                println(collider);
            }
            pause(PAUSE);

            if (ball.getY() > getHeight()) {
                println("FAIL");
                turns--;
                break;
            }
        }
    }

    private void centerBall() {
        ball.setLocation(getWidth() / 2 - BALL_RADIUS,
                getHeight() / 2 - BALL_RADIUS);
    }


    /**
     * This method implements check, if the ball
     * collides with some object and
     *
     * @return GObject|null Object, that collides with the ball.
     */
    private GObject getCollidingObject() {
        double[][] coords = {
                {ball.getX(), ball.getY()},
                {ball.getX() + 2 * BALL_RADIUS, ball.getY()},
                {ball.getX(), ball.getY() + 2 * BALL_RADIUS},
                {ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS}
        };

        for (double[] coord : coords) {
            GObject collider = getElementAt(coord[0], coord[1]);
            if (collider != null) {
                return collider;
            }
        }

        return null;
    }


    /**
     * This method implements check, if the ball
     * touches the wall. It prevents ball flying away
     * and change direction of the ball to opposite.
     */
    private void checkWallsChangeDirection() {
        if (ball.getX() <= 0 || (ball.getX() >= getWidth() - ball.getWidth())) {
            vx = -vx;
        }
        if (ball.getY() <= 0) {
            vy = -vy;
        }
    }

    /**
     * Creating of the ball, that will be
     * animated later.
     */
    private void addBall() {
        ball = new GOval(getWidth() / 2 - BALL_RADIUS,
                getHeight() / 2 - BALL_RADIUS,
                BALL_RADIUS * 2,
                BALL_RADIUS * 2
        );
        ball.setColor(Color.BLACK);
        ball.setFilled(true);
        add(ball);
    }


    /**
     * Creating of the paddle.
     * It will move horizontally but always
     * be on the same y-position.
     */
    private void addPaddle() {
        paddle = new GRect(0,
                getHeight() - PADDLE_Y_OFFSET,
                PADDLE_WIDTH,
                PADDLE_HEIGHT
        );
        paddle.setColor(Color.BLACK);
        paddle.setFilled(true);
        add(paddle);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, paddle.getY());
        mouseExited(e);
    }

    /**
     * This method implements check if the mouse
     * is out of the application window
     * and set position to the paddle in such cases,
     * so that the paddle stays within the window.
     */
    public void mouseExited(MouseEvent e) {
        if (e.getX() < PADDLE_WIDTH / 2) {
            paddle.setLocation(0, paddle.getY());
        }
        if (e.getX() > getWidth() - PADDLE_WIDTH / 2) {
            paddle.setLocation(getWidth() - PADDLE_WIDTH, paddle.getY());
        }
    }


}