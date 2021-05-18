package com.shpp.p2p.cs.lzhukova.assignment17.assignment13OnMyCollections;

import com.shpp.p2p.cs.lzhukova.assignment17.MyArrayList;
import com.shpp.p2p.cs.lzhukova.assignment17.MyHashmap;
import com.shpp.p2p.cs.lzhukova.assignment17.MyLinkedList;

import java.util.Map;
import java.util.Objects;


public class BreadFirstSearch {

    private final int[][] pixelArr;
    private int objectsCounter;
    private int pixelsInObject;

    public BreadFirstSearch(int[][] pixelArr) {
        this.pixelArr = pixelArr;
    }

    /**
     * Method implements search of available silhouettes at the picture,
     * and namely - search in the hashmap for "false" values,
     * that means, that such vertices weren't visited before;
     * it takes the first key with "false" value and starts bread-first-search from this pixel;
     * The "for" loop repeats, until all the values are "true"; It means, that all pixels(vertices) are visited;
     */
    public MyArrayList<Integer> lookForSilhouettes(MyHashmap<Integer, Boolean> verticesMap) {
        MyArrayList<Integer> silhouettes = new MyArrayList<>();
        // counter of the objects (silhouettes);
        objectsCounter = 0;
        // counter of pixels in an object;
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

    /**
     * This method implements specifically bread-first-search;
     *
     * @param verticesMap - hashmap with vertices(key) and their status(false - not visited, true - visited)
     * @param vertex      - pixel, from which bread-first-search starts;
     * @param silhouettes - arraylist, that contains amount of pixels in each silhouette;
     */
    public void bfs(MyHashmap<Integer, Boolean> verticesMap, int vertex, MyArrayList<Integer> silhouettes) {
        MyLinkedList<Integer> listOfVertices = new MyLinkedList<>();

        listOfVertices.add(vertex);

        while (!listOfVertices.isEmpty()) {

            // counter of pixels in one specific silhouette
            // with each iteration amount of pixels in the specific silhouettes increases by 1
            pixelsInObject += 1;
            silhouettes.set(objectsCounter, pixelsInObject);

            // the first removed element from the linked list will be put to the hashmap as visited
            int visitedVertex = listOfVertices.pollFirst();
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
