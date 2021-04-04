package com.shpp.p2p.cs.lzhukova.assignment12;

import acm.graphics.GImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FindingSilhouettes {
    private final ArrayList<Integer> Silhouettes;
    int SilhouettesCount;
    int[][] pixelArr;
    HashMap<Integer, Boolean> vertices;
    private int vertexNum;
    private int counter = 0;
    private int pixelsNum = 0;

    public FindingSilhouettes(GImage image) {
        Silhouettes = new ArrayList<>();

        SilhouettesCount = 0;

        vertices = new HashMap<>();
        pixelArr = image.getPixelArray();
        getVertices();
        find();
    }

    public void find() {
        lookForSilhouette();
        countCorrectSilhouettes();
    }

    private void countCorrectSilhouettes() {
        for (int i = 0; i < Silhouettes.size(); i++) {
            if (Silhouettes.get(i) > 150)
                SilhouettesCount += 1;
        }
        System.out.println((SilhouettesCount <= 1 ? "There is " : "There are ") + SilhouettesCount +
                (SilhouettesCount == 1 ? " silhouette " : " silhouettes ") +
                "at the picture.");
    }

    private void lookForSilhouette() {

        for (Map.Entry<Integer, Boolean> entry : vertices.entrySet()) {
            if (Objects.equals(false, entry.getValue())) {
                int startVertex = entry.getKey();

                Silhouettes.add(counter, pixelsNum);

                dfs(startVertex);
                pixelsNum = 0;
                counter += 1;
            }

        }
    }


    private void dfs(int vertex) {
        pixelsNum += 1;
        Silhouettes.set(counter, pixelsNum);
        vertices.put(vertex, true);
//        System.out.println(vertex);

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


    private int getPixelColor(int y, int x) {
        int bgRed = GImage.getRed(pixelArr[y][x]);
        int bgGreen = GImage.getGreen(pixelArr[y][x]);
        int bgBlue = GImage.getBlue(pixelArr[y][x]);
        return (bgRed + bgGreen + bgBlue) / 3;
    }

    private void getVertices() {
        int BGAlpha = getAlpha(0, 0);
        System.out.println(BGAlpha);
        if (BGAlpha != 0) {
            searchVerticesForColor();
        } else {
            searchVerticesForAlpha(BGAlpha);
        }

    }

    private void searchVerticesForAlpha(int BGAlpha) {
        for (int y = 0; y < pixelArr.length; y++) {
            for (int x = 0; x < pixelArr[0].length; x++) {
                vertexNum += 1;
                if (getAlpha(y, x) != BGAlpha) {
                    vertices.put(vertexNum, false);
                }
            }
        }
    }

    private void searchVerticesForColor() {
        for (int y = 0; y < pixelArr.length; y++) {
            for (int x = 0; x < pixelArr[0].length; x++) {
                vertexNum += 1;
                if (getPixelColor(y, x) < 150) {
                    vertices.put(vertexNum, false);
                }
            }
        }
    }

    private int getAlpha(int y, int x) {
        return GImage.getAlpha(pixelArr[y][x]);
    }
}
