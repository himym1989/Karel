package com.shpp.p2p.cs.lzhukova.assignment12test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FindSil implements Constants {
    private final ArrayList<Integer> Silhouettes;
    private final BufferedImage image;

    int SilhouettesCounter;

    /**
     * Hashmap, that contains number of vertex and its status:
     * - false, if vertex is not visited;
     * - true, if vertex is visited;
     */
    HashMap<Integer, Boolean> vertices;

    private int vertexCounter;
    private int pixelsCounter;

    /**
     * counter-variable, that contains amount of pixels in each silhouette;
     */
    private int pixelsAmount;

    public FindSil(BufferedImage image) {
        this.image = image;
        Silhouettes = new ArrayList<>();
        SilhouettesCounter = 0;
        vertices = new HashMap<>();
        searchVertices();
        lookForSilhouettes();
    }

    private void countSilhouettes() {
        for (Integer silhouette : Silhouettes) {
            if (silhouette > (image.getWidth() * image.getHeight()) * MIN_SCALE) {
                SilhouettesCounter += 1;
            }
        }
    }

    /**
     * Method implements search of available silhouettes at the picture,
     * and  namely - search in the hashmap for "false" values,
     * that means, that such vertices weren't visited before;
     */
    public void lookForSilhouettes() {

        for (Map.Entry<Integer, Boolean> entry : vertices.entrySet()) {
            if (Objects.equals(false, entry.getValue())) {
                int startVertex = entry.getKey();

                Silhouettes.add(pixelsCounter, pixelsAmount);

                dfs(startVertex);

                pixelsCounter += 1;
                pixelsAmount = 0;
            }
        }
        countSilhouettes();
        printResult();
    }

    public String printResult() {
        return (SilhouettesCounter <= 1 ? "There is " : "There are ") + SilhouettesCounter +
                (SilhouettesCounter <= 1 ? " silhouette " : " silhouettes ") +
                "at the picture.";
    }


    /**
     * Method implements deep-first-search. Amount of pixels in each silhouettes is saved to the
     * arrayList for later verification;
     */
    private void dfs(int vertex) {
        pixelsAmount += 1;
        Silhouettes.set(pixelsCounter, pixelsAmount);

        vertices.put(vertex, true);

        int down = vertex + image.getHeight();
        int up = vertex - image.getHeight();
        int right = vertex + 1;
        int left = vertex - 1;

        if (vertices.containsKey(down) && !vertices.get(down)) {
            dfs(down);
        }

        if (vertices.containsKey(right) && !vertices.get(right)) {
            dfs(right);
        }
        if (vertices.containsKey(up) && !vertices.get(up)) {
            dfs(up);
        }
        if (vertices.containsKey(left) && !vertices.get(left)) {
            dfs(left);
        }
    }

    /**
     * This method implements search of vertices in the 2d-array;
     * Pixels with less then minimum valuable alpha and minimum valuable luminance will be saved
     * to the hashmap for later silhouettes search;
     */
    private void searchVertices() {

        int firstPixel = image.getRGB(0, 0);
        boolean isLightBg = getAlpha(firstPixel) < MIN_VALUABLE_ALPHA ||
                getLuminance(firstPixel) > MIN_VALUABLE_LUMINANCE;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                vertexCounter += 1;

                int pixel = image.getRGB(x, y);

                if (getAlpha(pixel) < MIN_VALUABLE_ALPHA) {
                    continue;
                }

                boolean isLightPixel = getLuminance(pixel) > MIN_VALUABLE_LUMINANCE;

                if ((isLightBg && !isLightPixel) || (!isLightBg && isLightPixel)) {
                    vertices.put(vertexCounter, false);
                }
            }
        }
    }

    private int getAlpha(int pixel) {
        return (pixel >> 24) & 255;
    }

    /**
     * Method implements getting luminance of a particular pixel;
     */
    private double getLuminance(int pixel) {
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = (pixel) & 0xFF;
        return (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;
    }
}
