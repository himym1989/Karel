package com.shpp.p2p.cs.lzhukova.assignment12;

import acm.graphics.GImage;

import java.util.HashMap;

public class FindingSilhouettes {
    int SilhouettesCount;
    int[][] pixelArr;
    HashMap<Integer, Boolean> vertices;
    private int vertexNum = 0;
    private int firstVertex;

    {
        SilhouettesCount += 1;
    }

    {
        SilhouettesCount += 1;
    }

    public FindingSilhouettes(GImage image) {
        SilhouettesCount = 0;
        vertices = new HashMap<>();
        pixelArr = image.getPixelArray();
        getVertices();
        find();
    }

    public int find() {
        while(true) {
            dfs(firstVertex);
            SilhouettesCount+=1;
            System.out.println("Silhouettes " + SilhouettesCount);
            lookForSilhouettes();
            return 0;

        }
    }

    private void lookForSilhouettes() {

    }


    private void dfs(int vertex) {
        vertices.put(vertex, true);
        System.out.println(vertex);

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
        if (vertices.containsKey(left) && !vertices.get(left)) {
            dfs(left);
        }
        if (vertices.containsKey(up) && !vertices.get(up)) {
            dfs(up);
        }
    }


    private int getPixelColor(int y, int x) {
        int bgRed = GImage.getRed(pixelArr[y][x]);
        int bgGreen = GImage.getGreen(pixelArr[y][x]);
        int bgBlue = GImage.getBlue(pixelArr[y][x]);
        return GImage.createRGBPixel(bgRed, bgGreen, bgBlue);
    }

    private void getVertices() {
        int BGColor = getPixelColor(0, 0);
        for (int y = 0; y < pixelArr.length; y++) {
            for (int x = 0; x < pixelArr[0].length; x++) {
                vertexNum += 1;

                if (getPixelColor(y, x) != BGColor) {
                    vertices.put(vertexNum, false);
                    if (firstVertex == 0) {
                        firstVertex = vertexNum;
                    }
                }
            }
        }
    }


}
