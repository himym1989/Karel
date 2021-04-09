package com.shpp.p2p.cs.lzhukova.assignment13;

import acm.graphics.GImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FindSilhouettes implements SilhouettesParamConstants {

    private final ArrayList<Integer> silhouettes;
    private final BreadFirstSearch bfs;

    /**
     * 2d - array, that is got from the picture;
     */
    private final int[][] pixelArr;
    /**
     * Hashmap, that contains number of vertex and its status:
     * - false, if vertex is not visited;
     * - true, if vertex is visited;
     */
    private final HashMap<Integer, Boolean> vertices;
    private final HashMap<Integer, Boolean> newVertices;
    int averageSilhouette;
    private int pixelCounter;

    public FindSilhouettes(GImage image) {

        pixelArr = image.getPixelArray();

        bfs = new BreadFirstSearch(pixelArr);

        vertices = new HashMap<>();
        newVertices = new HashMap<>();


        searchVertices();

        silhouettes = bfs.lookForSilhouettes(vertices);
        findAverageSilhouette();
    }


    /**
     * This method implements counting of silhouettes and throwing out garbage using MIN_SCALE constant,
     * that fixes size of garbage in 0.01 percent from the image size;
     */
    private void findAverageSilhouette() {
        int sum = 0;
        int silhouettesAmount = 0;

        for (Integer silhouette : silhouettes) {
            if (silhouette > (pixelArr.length * pixelArr[0].length) * MIN_SCALE) {
                sum += silhouette;
                silhouettesAmount += 1;
            }
        }
        averageSilhouette = sum / silhouettesAmount;

        System.out.println("Silhouettes before erosion... " + silhouettesAmount);
        System.out.println("Average silhouette... " + averageSilhouette);
        System.out.println();

        int erosionCoefficient;
        if (averageSilhouette > 500000) {
            erosionCoefficient = 100;
            imageErosion(erosionCoefficient);
        }
        else if (averageSilhouette > 100000) {
            erosionCoefficient = 30;
            imageErosion(erosionCoefficient);
        }
        else if (averageSilhouette > 20000) {
            erosionCoefficient = 20;
            imageErosion(erosionCoefficient);
        } else if (averageSilhouette > 10000 && averageSilhouette < 20000) {
            erosionCoefficient = 10;
            imageErosion(erosionCoefficient);
        } else if (averageSilhouette > 500 && averageSilhouette < 10000) {
            erosionCoefficient = 1;
            imageErosion(erosionCoefficient);
        } else {
            System.out.println(printResult(silhouettesAmount));
        }
    }

    /**
     * This method implements counting of silhouettes and throwing out garbage using MIN_SCALE constant,
     * that fixes size of garbage in 0.01 percent from the image size;
     */
    private void countSilhouettes(ArrayList<Integer> silhouettesAfterErosion) {
        int silhouettesCounter = 0;
        double coeff;
        if (averageSilhouette > 100000) {
            coeff = 0.05;
        } else {
            coeff = 0.1;
        }
        for (Integer silhouette : silhouettesAfterErosion) {
            if (silhouette > averageSilhouette * coeff) {
                silhouettesCounter += 1;
            }

        }
        System.out.println(printResult(silhouettesCounter));
    }

    private void imageErosion(int erosionCoefficient) {
        System.out.println("start erosion");

        for (Map.Entry<Integer, Boolean> entry : vertices.entrySet()) {
            int vertex = entry.getKey();

            int down = vertex + pixelArr[0].length * erosionCoefficient + erosionCoefficient;
            int up = vertex - pixelArr[0].length * erosionCoefficient + erosionCoefficient;
            int right = vertex + erosionCoefficient;
            int left = vertex - erosionCoefficient;

            if (vertices.containsKey(down) && vertices.containsKey(up) && vertices.containsKey(right) && vertices.containsKey(left)) {
                newVertices.put(vertex, false);
            }
        }

        ArrayList<Integer> silhouettesAfterErosion = bfs.lookForSilhouettes(newVertices);

        System.out.println(silhouettesAfterErosion);
        countSilhouettes(silhouettesAfterErosion);
    }


    public String printResult(int silhouettesAmount) {
        return (silhouettesAmount <= 1 ? "There is " : "There are ") + silhouettesAmount +
                (silhouettesAmount <= 1 ? " silhouette " : " silhouettes ") +
                "at the picture.";
    }

    /**
     * Method implements getting luminance of a particular pixel;
     */
    private double getPixelLuminance(int pixel) {
        int red = GImage.getRed(pixel);
        int green = GImage.getGreen(pixel);
        int blue = GImage.getBlue(pixel);
        /* formula for finding luminance of the color */
        return (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;
    }

    /**
     * This method implements search of vertices in the 2d-array;
     * Pixels with less then minimum valuable alpha and minimum valuable luminance will be saved
     * to the hashmap for later silhouettes search;
     */
    private void searchVertices() {
        boolean isLightBg = GImage.getAlpha(pixelArr[0][0]) == 0 || getPixelLuminance(pixelArr[0][0]) > MIN_VALUABLE_LUMINANCE;
//        boolean isLightBg = getPixelLuminance(pixelArr[0][0]) > MIN_VALUABLE_LUMINANCE;


        for (int y = 0; y < pixelArr.length; y++) {
            for (int x = 0; x < pixelArr[0].length; x++) {
                int pixel = pixelArr[y][x];

                pixelCounter += 1;

                int alpha = GImage.getAlpha(pixel);
                if (alpha < MIN_VALUABLE_ALPHA) {
                    continue;
                }


                int color = (GImage.getRed(pixel) + GImage.getGreen(pixel) + GImage.getBlue(pixel)) / 3;

                boolean isLightPixel = color > MIN_VALUABLE_COLOR;

                if ((isLightBg && !isLightPixel) || (!isLightBg && isLightPixel)) {
                    vertices.put(pixelCounter, false);
                }
            }
        }


//        for (int y = 0; y < pixelArr.length; y++) {
//            for (int x = 0; x < pixelArr[0].length; x++) {
//                int pixel = pixelArr[y][x];
//
//                pixelCounter += 1;
////
////                int alpha = GImage.getAlpha(pixel);
////                if (alpha < MIN_VALUABLE_ALPHA) {
////                    continue;
////                }
//
//                boolean isLightPixel = getPixelLuminance(pixel) > MIN_VALUABLE_LUMINANCE;
//
//                if ((isLightBg && !isLightPixel) || (!isLightBg && isLightPixel)) {
//                    vertices.put(pixelCounter, false);
//                }
//            }
//        }
    }
}
