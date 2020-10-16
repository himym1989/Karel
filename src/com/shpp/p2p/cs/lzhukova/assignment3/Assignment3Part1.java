package com.shpp.p2p.cs.lzhukova.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * This programs is used for determination,
 * if the user did enough physical exercises
 * for his/her cardiovascular and blood pressure
 * health during a week.
 */
public class Assignment3Part1 extends TextProgram {

    /* This constant contains amount of days needed for training per week */
    private static final int DAYS_FOR_CARDIOHEALTH_NEEDED = 5;
    private static final int DAYS_FOR_PRESSUREHEALTH_NEEDED = 3;

    /* This constant contains time needed for training per day */
    private static final int TIME_FOR_CARDIO_TRAINING_NEEDED = 30;
    private static final int TIME_FOR_PRESSURE_TRAINING_NEEDED = 40;

    /* this variable contains data, read from the user */
    private int trainingTime = 0;

    /* this variable will contain total amount of training-days,
     * that fulfill the requirements */
    private int cardioTrainingDays = 0;
    private int pressureTrainingDays = 0;

    public void run() {
        readDataFromUser();
        printResult();
    }

    /**
     * This method reads data from user and counts total amount of training-days,
     * that fulfill the requirements: 30 minutes per day for cardiovascular health,
     * 40 minutes per day for blood pressure health.
     */
    private void readDataFromUser() {
        for (int i = 1; i <= 7; i++) {
            trainingTime = readInt("How many minutes did you do on day " + i + "?");
            while (trainingTime <= 0) {
                println("Time can't be reversed. ");
                trainingTime = readInt("put a positive integer: ");
            }
            if (trainingTime >= TIME_FOR_CARDIO_TRAINING_NEEDED) {
                cardioTrainingDays += 1;
            }
            if (trainingTime >= TIME_FOR_PRESSURE_TRAINING_NEEDED) {
                pressureTrainingDays += 1;
            }
        }
    }

    /**
     * This method counts and print the result:
     * five trainings per week must be done for cardiovascular health,
     * three - for blood pressure.
     * If the plan is not completed, the user gets a recommendation
     * to train more.
     */
    private void printResult() {
        String cardioResult = cardioTrainingDays >= DAYS_FOR_CARDIOHEALTH_NEEDED ? "Great job! You've done enough exercise for cardiovacular health." :
                "You needed to train hard for at least " + (DAYS_FOR_CARDIOHEALTH_NEEDED - cardioTrainingDays) + " more day(s) a week!";
        String pressureResult = pressureTrainingDays >= DAYS_FOR_PRESSUREHEALTH_NEEDED ? "Great job! You've done enough exercise to keep a low blood pressure." :
                "You needed to train hard for at least " + (DAYS_FOR_PRESSUREHEALTH_NEEDED - pressureTrainingDays) + " more day(s) a week!";

        println("Cardiovascular health:\n" + cardioResult);
        println("Blood pressure:\n" + pressureResult);
    }
}
