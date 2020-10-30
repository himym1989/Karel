package com.shpp.p2p.cs.lzhukova.assignment4;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This is a breakout game. Rules are easy:
 * the game starts when the gamer clicked a mouse.
 * He/she has three turns to destroy rows of bricks.
 * The game ends in two cases:
 * 1) there are no bricks left on the screen.
 * in this case the gamer wins;
 * 2) the gamer used all three turns, but some bricks are left.
 * in this case the gamer lost the game.
 */

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


    /**
     * Number of turns
     */
    private static final int NTURNS = 3;


    /**
     * Maximum x-velocity of the ball.
     */
    private static final double MAX_X_VELOCITY = 5;


    /**
     * Y-velocity of the ball.
     */
    private static final double Y_VELOCITY = 8;


    private GRect paddle;
    private GOval ball;

    /* variables, that control move direction and velocity of the ball */
    private double vx;
    private double vy;

    /**
     * Amount of turns, will change in case of ball dropping down
     */
    private int turns = NTURNS;

    /**
     * Amount of bricks on the screen, will change with every collision of the ball and a brick
     */
    private int bricksAmount = NBRICK_ROWS * NBRICKS_PER_ROW;

    public void run() {
        addBricks();
        addPaddle();
        addBall();

        addMouseListeners();

        startGame();

    }

    /**
     * This method implements the whole cycle of game with three turns.
     */
    private void startGame() {
        bricksAmount = NBRICK_ROWS * NBRICKS_PER_ROW;
        turns = NTURNS;

        while (turns > 0 && bricksAmount > 0) {
            centerBall();
            waitForClick();
            playGame();
        }
        checkResult();
    }

    /**
     * Method implements one turn of breakout game.
     */
    private void playGame() {
        RandomGenerator rgen = RandomGenerator.getInstance();
        if (rgen.nextBoolean(0.5)) {
            vx = -vx;
        }
        vy = Y_VELOCITY;

        while (bricksAmount > 0) {
            checkWallsChangeDirection();

            ball.move(vx, vy);
            GObject collider = getCollidingObject();

            checkColliderType(rgen, collider);

            pause(PAUSE);

            if (lossConfirmed()) {
                break;
            }
        }
    }

    /**
     * Check of the collider type in order to perform actions
     * depending on its type.
     */
    private void checkColliderType(RandomGenerator rgen, GObject collider) {
        if (collider == paddle) {
            vy = -vy;
        } else if (collider != null) {
            vy = -vy;
            vx = rgen.nextDouble(1.0, MAX_X_VELOCITY);
            remove(collider);
            bricksAmount--;
        }
    }

    /**
     * Method implements check if the gamer lost the turn.
     * It happens when the ball goes down the window height.
     */
    private boolean lossConfirmed() {
        if (ball.getY() > getHeight()) {
            turns--;
            return true;
        }
        return false;
    }

    /**
     * When the game is over, its time to check if the gamer lost or won.
     */
    private void checkResult() {
        if (bricksAmount == 0 || turns == 0) {
            remove(ball);
            remove(paddle);
            printResultInfo();
        }
    }

    /**
     * Method implements result output.
     * It depends on if the gamer lost or won.
     */
    private void printResultInfo() {
        GLabel resultInfo = new GLabel(
                turns > 0 ? "Congrats! You win the game!" : "Sorry! You lost the game!");
        resultInfo.setFont("Serif-30");
        add(resultInfo, getWidth(), (getHeight() + resultInfo.getAscent()) / 2);
        while (resultInfo.getX() > (getWidth() - resultInfo.getWidth()) / 2) {
            resultInfo.move(-4, 0);
            pause(PAUSE);
        }
    }

    /**
     * Adding bricks to the application window.
     * The amount of the bricks are controlled by constants.
     * In this game there are 10 rows, with 10 bricks in each.
     */
    private void addBricks() {
        double xOffset = (getWidth() - (BRICK_WIDTH * NBRICKS_PER_ROW + BRICK_SEP * (NBRICKS_PER_ROW - 1))) / 2f;

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
     * @return - color of the rows.
     */
    private Color getBrickColor(int row) {
        if (row < 2) return Color.RED;
        if (row == 2 || row == 3) return Color.ORANGE;
        if (row == 4 || row == 5) return Color.YELLOW;
        if (row == 6 || row == 7) return Color.GREEN;
        return Color.CYAN;
    }

    /**
     * Setting ball location at the center of the app window.
     */
    private void centerBall() {
        ball.setLocation(getWidth() / 2f - BALL_RADIUS,
                getHeight() / 2f - BALL_RADIUS);
    }

    /**
     * This method implements check, if the ball
     * collides with some object and
     *
     * @return GObject|null Object, that collides with the ball.
     */
    private GObject getCollidingObject() {
        boolean isUpDirection = vy < 0;
        ArrayList<double[]> coords = getBallOuterCoords();
        GObject collider;

        for (double[] coord : coords) {
            for (double i = 0; i <= Math.abs(vy); i++) {
                collider = getElementAt(coord[0], coord[1] + (isUpDirection ? -i : i));
                if (collider != null) {
                    return collider;
                }
            }
        }

        return null;
    }

    /**
     * This method creates ArrayList of ball coordinates depending on
     * its direction. It serves for avoiding bags with incorrect ball-collision,
     * that emerges with increasing y-velocity.
     *
     * @return ArrayList of arrays with coordinates.
     */
    private ArrayList<double[]> getBallOuterCoords() {
        boolean isUpDirection = vy < 0;
        // adding additional bound to the ball in case to avoid the ball colliding itself.
        int additionalBound = 1;
        ArrayList<double[]> coords = new ArrayList<>();

        if (isUpDirection) {
            coords.add(new double[]{ball.getX(), ball.getY()}); // top left
            coords.add(new double[]{ball.getX() + BALL_RADIUS, ball.getY() - additionalBound}); // top-middle
            coords.add(new double[]{ball.getX() + 2 * BALL_RADIUS, ball.getY()}); // top-right
        } else {
            coords.add(new double[]{ball.getX(), ball.getY() + 2 * BALL_RADIUS}); // bottom-left
            coords.add(new double[]{ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS}); // bottom-middle
            coords.add(new double[]{ball.getX() + BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS + additionalBound}); // br
        }

        // coords.add(new double[]{ball.getX() - additionalBound, ball.getY() + BALL_RADIUS}); // ml
        // coords.add(new double[]{ball.getX() + 2 * BALL_RADIUS + additionalBound, ball.getY() + BALL_RADIUS}); // mr

        return coords;
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
        ball = new GOval(getWidth() / 2f - BALL_RADIUS,
                getHeight() / 2f - BALL_RADIUS,
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

    /**
     * Mouse moves control paddle moves on x-coordinate,
     * mouse linked to the paddle-center.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        paddle.setLocation(e.getX() - PADDLE_WIDTH / 2f, paddle.getY());
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