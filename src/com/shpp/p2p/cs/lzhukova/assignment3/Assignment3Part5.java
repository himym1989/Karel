package com.shpp.p2p.cs.lzhukova.assignment3;

import acm.util.RandomGenerator;
import com.shpp.cs.a.console.TextProgram;

/**
 * This program imitates casino play.
 * Rules are easy: the gamer makes his bet 1$;
 * If a coin is flipped in the 'heads up' position,
 * the gamers's gain doubles. Else - he lost money.
 * The game should stop, if total gain 20$ or more.
 */
public class Assignment3Part5 extends TextProgram {

    /* Minimal gain, based on rules of the game */
    private static final int MINIMAL_GAIN = 20;
    // random generator is used for imitating of coin flipping
    RandomGenerator rg = RandomGenerator.getInstance();

    public void run() {
        // gamer's gain in one particular game;
        int gain = 1;
        int totalGain = 0;
        // game amount, that will be needed to win at least 20$
        int gameAmount = 0;

        playTheGame(gain, totalGain, gameAmount);
    }

    /**
     * This method describes the point of the game.
     * If the result of coin flipping is heads -
     * the gamer gain doubles, if tails - he lost
     * his bet.
     *
     * @param gain       - int, money, that gamer gets in one game.
     * @param totalGain  - int,  total gain in all games
     * @param gameAmount - int, amount of games, that were needed
     *                   to win at least 20$
     */
    private void playTheGame(int gain, int totalGain, int gameAmount) {
        while (totalGain < MINIMAL_GAIN) {
            gameAmount++;
            if (isHeads()) {
                gain *= 2;
            }
            totalGain += gain;
            println("This game you earned " + gain + "$");
            println("Your total is " + totalGain + "$");
        }
        println("It took " + gameAmount + " games to earn 20$");
    }

    /**
     * Method imitates coin flipping and returns
     *
     * @return true, if (bet == 1)(imitates heads after coin flipping)
     */
    private boolean isHeads() {
        int bet = rg.nextInt(2);
        return bet == 1;
    }

}
