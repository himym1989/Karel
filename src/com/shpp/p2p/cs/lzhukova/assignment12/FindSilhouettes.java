package com.shpp.p2p.cs.lzhukova.assignment12;

import acm.graphics.GImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * This class represents searching silhouettes using dfs-algorithm.
 * It can handle png-files as well as jpg-files.
 */
public class FindSilhouettes implements SilhouettesParamConstants {

    private final ArrayList<Integer> Silhouettes;

    int SilhouettesCounter;

    /**
     * 2d - array, that is got from the picture;
     */
    int[][] pixelArr;

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

    public FindSilhouettes(GImage image) {
        Silhouettes = new ArrayList<>();
        SilhouettesCounter = 0;
        vertices = new HashMap<>();
        pixelArr = image.getPixelArray();
        searchVertices();
        lookForSilhouettes();
    }

    private void countSilhouettes() {
        for (Integer silhouette : Silhouettes) {
            if (silhouette > (pixelArr.length * pixelArr[0].length) * MIN_SCALE) {
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
     *
     * @param vertex
     */
    private void dfs(int vertex) {
        pixelsAmount += 1;
        Silhouettes.set(pixelsCounter, pixelsAmount);
        vertices.put(vertex, true);

        int down = vertex + pixelArr[0].length;
        int up = vertex - pixelArr[0].length;
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
     * Method implements getting luminance of a particular pixel;
     */
    private double getPixelLuminance(int y, int x) {
        int red = GImage.getRed(pixelArr[y][x]);
        int green = GImage.getGreen(pixelArr[y][x]);
        int blue = GImage.getBlue(pixelArr[y][x]);
        return (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;
    }

    /**
     * This method implements search of vertices in the 2d-array;
     * Pixels with less then minimum valuable alpha and minimum valuable luminance will be saved
     * to the hashmap for later silhouettes search;
     */
    private void searchVertices() {
        for (int y = 0; y < pixelArr.length; y++) {
            for (int x = 0; x < pixelArr[0].length; x++) {
                vertexCounter += 1;
                int alpha = GImage.getAlpha(pixelArr[y][x]);

                if (alpha < MIN_VALUABLE_ALPHA) {
                    continue;
                }
                if (getPixelLuminance(y, x) < MIN_VALUABLE_LUMINANCE) {
                    vertices.put(vertexCounter, false);
                }
            }
        }
    }
}
