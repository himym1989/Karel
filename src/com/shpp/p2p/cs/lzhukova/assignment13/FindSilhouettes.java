package com.shpp.p2p.cs.lzhukova.assignment13;

import acm.graphics.GImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FindSilhouettes implements SilhouettesParamConstants {

    private final ArrayList<Integer> silhouettes;
    private final BreadFirstSearch bfs;
    int silhouettesCounter;
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
    private final HashMap<Integer, Boolean> erasedVertices;
    int averageSilhouette;

    public FindSilhouettes(GImage image) {

        pixelArr = image.getPixelArray();

        bfs = new BreadFirstSearch(pixelArr);

        vertices = new HashMap<>();
        erasedVertices = new HashMap<>();

        silhouettesCounter = 0;

        searchVertices();
        silhouettes = bfs.lookForSilhouettes(vertices);
        findAverageSilhouette();
        imageErosion(averageSilhouette);
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
    }

    /**
     * This method implements counting of silhouettes and throwing out garbage using MIN_SCALE constant,
     * that fixes size of garbage in 0.01 percent from the image size;
     */
    private void countSilhouettes(ArrayList<Integer> silhouettesAfterErosion) {

        for (Integer silhouette : silhouettesAfterErosion) {
            if (silhouette > averageSilhouette * AFTER_EROSION_SCALE) {
                silhouettesCounter += 1;
            }

        }
        System.out.println(printResult());
        System.out.println();
    }

    private void imageErosion(int averageSilhouette) {
        System.out.println("Starting erosion...");
        int erosionCoefficient = 0;

        Map<Integer, Integer> erosionCoefficients = Stream.of(new Object[][]{
                {500000, 100},
                {100000, 30},
                {20000, 20},
                {10000, 10},
                {500, 1},
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (Integer) data[1]));

        for (Integer key : erosionCoefficients.keySet()) {
            if (averageSilhouette > key) {
                erosionCoefficient = erosionCoefficients.get(key);
                break;
            }
        }

        for (Map.Entry<Integer, Boolean> entry : vertices.entrySet()) {
            int vertex = entry.getKey();

            int down = vertex + pixelArr[0].length * erosionCoefficient + erosionCoefficient;
            int up = vertex - pixelArr[0].length * erosionCoefficient + erosionCoefficient;
            int right = vertex + erosionCoefficient;
            int left = vertex - erosionCoefficient;

            if (vertices.containsKey(down) && vertices.containsKey(up) && vertices.containsKey(right) && vertices.containsKey(left)) {
                erasedVertices.put(vertex, false);
            }
        }

        ArrayList<Integer> silhouettesAfterErosion = bfs.lookForSilhouettes(erasedVertices);
        countSilhouettes(silhouettesAfterErosion);
    }


    public String printResult() {
        return (silhouettesCounter <= 1 ? "There is " : "There are ") + silhouettesCounter +
                (silhouettesCounter <= 1 ? " silhouette " : " silhouettes ") +
                "at the picture after erosion.";
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
        int pixelCounter = 0;

        for (int y = 0; y < pixelArr.length; y++) {
            for (int x = 0; x < pixelArr[0].length; x++) {

                int pixel = pixelArr[y][x];
                pixelCounter += 1;

                int color = (GImage.getRed(pixel) + GImage.getGreen(pixel) + GImage.getBlue(pixel)) / 3;
                boolean isLightPixel = color > MIN_VALUABLE_COLOR;

                int alpha = GImage.getAlpha(pixel);
                if (alpha < MIN_VALUABLE_ALPHA) {
                    continue;
                }

                if ((isLightBg && !isLightPixel) || (!isLightBg && isLightPixel)) {
                    vertices.put(pixelCounter, false);
                }
            }
        }
    }
}
