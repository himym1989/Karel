package com.shpp.p2p.cs.lzhukova.assignment6.Assignment6Part3;

public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];
        double maxIntense = 0;

        for (int i = 0; i < toneMatrix.length; i++) {
            if (toneMatrix[i][column]) {
                for (int j = 0; j < samples[i].length; j++) {
                    result[j] += samples[i][j];
                    if (maxIntense < Math.abs(result[j])) {
                        maxIntense = Math.abs(result[j]);
                    }
                }
            }
        }

        for (int i = 0; i < result.length; i++) {
            if (result[i] == 0) {
                continue;
            }
            result[i] = result[i] / maxIntense;
        }

        return result;
    }
}
