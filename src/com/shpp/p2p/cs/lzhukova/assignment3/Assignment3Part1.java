package com.shpp.p2p.cs.lzhukova.assignment3;

import com.shpp.cs.a.console.TextProgram;


public class Assignment3Part1 extends TextProgram {

    private static final int DAYS_FOR_CARDIOHEALTH_NEEDED = 5;
    private static final int DAYS_FOR_PRESSUREHEALTH_NEEDED = 3;

    private static final int TIME_FOR_CARDIO_TRAINING_NEEDED = 30;
    private static final int TIME_FOR_PRESSURE_TRAINING_NEEDED = 40;

    int trainingTime = 0;
    int cardioTrainingDays = 0;
    int pressureTrainingDays = 0;

    public void run() {
        readData();
        printResult();
    }

    private void readData() {
        for (int i = 1; i <= 7; i++) {
            trainingTime = readInt("How many minutes did you do on day " + i + "?");
            if (trainingTime >= TIME_FOR_CARDIO_TRAINING_NEEDED) {
                cardioTrainingDays += 1;
            }
            if (trainingTime >= TIME_FOR_PRESSURE_TRAINING_NEEDED) {
                pressureTrainingDays += 1;
            }
        }
    }

    private void printResult() {
        String cardioResult = cardioTrainingDays >= DAYS_FOR_CARDIOHEALTH_NEEDED ? "Great job! You've done enough exercise for cardiovacular health." :
                "You needed to train hard for at least " + (DAYS_FOR_CARDIOHEALTH_NEEDED - cardioTrainingDays) + " more day(s) a week!";
        String pressureResult = pressureTrainingDays >= DAYS_FOR_PRESSUREHEALTH_NEEDED ? "Great job! You've done enough exercise to keep a low blood pressure." :
                "You needed to train hard for at least " + (DAYS_FOR_PRESSUREHEALTH_NEEDED - pressureTrainingDays) + " more day(s) a week!";

        println("Cardiovacular health:\n" + cardioResult);
        println("Blood pressure:\n" + pressureResult);
    }
}
