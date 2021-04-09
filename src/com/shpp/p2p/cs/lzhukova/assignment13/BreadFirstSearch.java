package com.shpp.p2p.cs.lzhukova.assignment13;

import java.util.*;

public class BreadFirstSearch {

    private int objectsCounter;
    private int pixelsInObject;
    private final int[][] pixelArr;


    public BreadFirstSearch(int[][] pixelArr) {
        this.pixelArr = pixelArr;
    }

    /**
     * Method implements search of available silhouettes at the picture,
     * and  namely - search in the hashmap for "false" values,
     * that means, that such vertices weren't visited before;
     */
    public ArrayList<Integer> lookForSilhouettes(HashMap<Integer, Boolean> verticesMap) {
        ArrayList<Integer> silhouettes = new ArrayList<>();
        objectsCounter = 0;
        pixelsInObject = 0;

        for (Map.Entry<Integer, Boolean> entry : verticesMap.entrySet()) {
            if (Objects.equals(false, entry.getValue())) {
                int startVertex = entry.getKey();

                silhouettes.add(objectsCounter, pixelsInObject);

                bfs(verticesMap, startVertex, silhouettes);

                objectsCounter += 1;
                pixelsInObject = 0;
            }
        }
        return silhouettes;
    }

    public void bfs(HashMap<Integer, Boolean> verticesMap, int vertex, ArrayList<Integer> silhouettes) {
        LinkedList<Integer> listOfVertices = new LinkedList<>();

        listOfVertices.add(vertex);


        while (!listOfVertices.isEmpty()) {

            // counter of pixels in one specific silhouette
            // with each iteration amount of pixels in the specific silhouettes increases by 1
            pixelsInObject += 1;
            silhouettes.set(objectsCounter, pixelsInObject);

            // the first removed element from the linked list will be put to the hashmap as visited
            int visitedVertex = listOfVertices.removeFirst();
            verticesMap.put(visitedVertex, true);

            int down = visitedVertex + pixelArr[0].length;
            int up = visitedVertex - pixelArr[0].length;
            int right = visitedVertex + 1;
            int left = visitedVertex - 1;

            // check all the directions from the last visited vertex and add to the linked list,
            // if the vertex in this direction hasn't been visited before
            int[] directions = {down, up, right, left};
            for (int direction : directions) {
                if (verticesMap.containsKey(direction) && !verticesMap.get(direction) && !listOfVertices.contains(direction)) {
                    listOfVertices.add(direction);
                }
            }

        }
    }

}
