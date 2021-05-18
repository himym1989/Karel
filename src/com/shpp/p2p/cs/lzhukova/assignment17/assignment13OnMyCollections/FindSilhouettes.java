package com.shpp.p2p.cs.lzhukova.assignment17.assignment13OnMyCollections;

import acm.graphics.GImage;
import com.shpp.p2p.cs.lzhukova.assignment17.MyArrayList;
import com.shpp.p2p.cs.lzhukova.assignment17.MyHashmap;

import java.util.Iterator;

/**
 * This class implements separation of silhouettes' pixels(so called vertices) from the background and saving them to
 * the hashmap for the further bread-first search, which task is to count silhouettes at the image;
 * <p>
 * So-called erosion is also implemented in this class, that aims to separate silhouettes, that stuck together;
 */
public class FindSilhouettes implements SilhouettesParamConstants {

    /* declaring a bread-first-search class */
    private final BreadFirstSearch bfs;
    /**
     * 2d - array, that is got from the picture;
     */
    private final int[][] pixelArr;
    /**
     * Hashmap, that contains number of vertex(key) and its status(value):
     * - false, if vertex is not visited;
     * - true, if vertex is visited;
     * is used for bfs before erosion
     */
    private final MyHashmap<Integer, Boolean> vertices;
    /**
     * Hashmap, that contains number of vertex(key) and its status(value):
     * - false, if vertex is not visited;
     * - true, if vertex is visited;
     * is used for bfs after erosion
     */
    private final MyHashmap<Integer, Boolean> erasedVertices;
    private final MyArrayList<Integer> silhouettes;
    GImage image;
    int silhouettesCounter;

    /* average size silhouettes */
    int averageSilhouette;

    public FindSilhouettes(GImage image) {
        this.image = alphaBlend(image);

        /* transforming image to array of pixels */
        pixelArr = this.image.getPixelArray();

        /* initializing a bread-first-search class */
        bfs = new BreadFirstSearch(pixelArr);

        vertices = new MyHashmap<>();
        erasedVertices = new MyHashmap<>();

        silhouettesCounter = 0;

        searchVertices();
        silhouettes = bfs.lookForSilhouettes(vertices);
        findAverageSilhouette();
        imageErosion(averageSilhouette);
        printResult();
    }


    public void printResult() {
        System.out.println((silhouettesCounter <= 1 ? "There is " : "There are ") + silhouettesCounter +
                (silhouettesCounter <= 1 ? " silhouette " : " silhouettes ") +
                "at the picture after erosion.");
    }

    /**
     * This method implements counting all the silhouettes before erosion
     * and finding an average size of a silhouette at the image; it is important for later erosion;
     */
    private void findAverageSilhouette() {
        int sum = 0;
        int silhouettesAmount = 0;

        Iterator<Integer> iterator = silhouettes.iterator();
        do {
            Integer silhouette = iterator.next();
            if (silhouette > (pixelArr.length * pixelArr[0].length) * MIN_SCALE) {
                sum += silhouette;
                silhouettesAmount += 1;
            }
        } while (iterator.hasNext());


        // size of an average silhouette is calculated only after silhouettes filter from garbage, in order
        // not to distort an average with tiny silhouettes;
        averageSilhouette = silhouettesAmount > 0 ? sum / silhouettesAmount : 0;

    }

    /**
     * This method implements counting of silhouettes after erosion
     * and throwing out "garbage" after erosion using AFTER_EROSION_SCALE constant;
     */
    private void countSilhouettes(MyArrayList<Integer> silhouettesAfterErosion) {
        Iterator<Integer> iterator = silhouettesAfterErosion.iterator();

        do {
            Integer silhouette = iterator.next();
            if (silhouette > averageSilhouette * AFTER_EROSION_SCALE) {
                silhouettesCounter += 1;
            }
        } while (iterator.hasNext());

    }


    /**
     * Method, that implements so-called image erosion depending on the average silhouette
     * at the image. It helps to chose correct erosion coefficient; it is very important, for example,
     * if the coefficient is the same for all the silhouettes - the biggest silhouettes can stay not separated
     * and the smallest can disappear in the process of erosion;
     *
     * @param averageSilhouette - integer value, average size of all the silhouettes at the picture;
     */
    private void imageErosion(int averageSilhouette) {
        int erosionCoefficient = 0;

        // https://www.baeldung.com/java-initialize-hashmap 4.3. Initializing an Immutable Map
        // filling the hashmap with keys(amount of pixels for one silhouette) and values (erosion "coefficient");
//        Map<Integer, Integer> erosionCoefficients = Stream.of(EROSION_COEFFICIENTS).collect(Collectors.toMap(data -> (Integer) data[0], data -> (Integer) data[1]));
        MyHashmap<Integer, Integer> erosionCoefficients = new MyHashmap<>();
        erosionCoefficients.put(500000, 100);
        erosionCoefficients.put(100000, 30);
        erosionCoefficients.put(20000, 20);
        erosionCoefficients.put(10000, 5);
        erosionCoefficients.put(7000, 3);


        for (Integer key : erosionCoefficients.keySet()) {
            if (averageSilhouette > key) {
                erosionCoefficient = erosionCoefficients.get(key);
                break;
            }
        }
// filling hashmap with vertices, that sustains erosion; they are defined by erosion coefficient;
        Iterator<Integer> mapIterator = vertices.keySet().iterator();
        do {

            int vertex = mapIterator.next();
            int down = vertex + pixelArr[0].length * erosionCoefficient + erosionCoefficient;
            int up = vertex - pixelArr[0].length * erosionCoefficient + erosionCoefficient;
            int right = vertex + erosionCoefficient;
            int left = vertex - erosionCoefficient;

            if (vertices.containsKey(down) && vertices.containsKey(up) && vertices.containsKey(right) && vertices.containsKey(left)) {
                erasedVertices.put(vertex, false);
            }
        } while (mapIterator.hasNext());

        MyArrayList<Integer> silhouettesAfterErosion = bfs.lookForSilhouettes(erasedVertices);

        countSilhouettes(silhouettesAfterErosion);
    }

    /**
     * This method implements search of vertices in the 2d-array;
     * Pixels with less then minimum valuable alpha and minimum valuable luminance will be saved
     * to the hashmap for later silhouettes search;
     */
    private void searchVertices() {

        boolean isLightBg = (
                GImage.getRed(pixelArr[0][0]) +
                        GImage.getGreen(pixelArr[0][0]) +
                        GImage.getBlue(pixelArr[0][0])) / 3 > MIN_VALUABLE_COLOR;

        int pixelCounter = 0;

        for (int y = 0; y < pixelArr.length; y++) {
            for (int x = 0; x < pixelArr[0].length; x++) {

                int pixel = pixelArr[y][x];
                pixelCounter += 1;

                int averageColor = (GImage.getRed(pixel) + GImage.getGreen(pixel) + GImage.getBlue(pixel)) / 3;
                boolean isLightPixel = averageColor > MIN_VALUABLE_COLOR;

                if ((isLightBg && !isLightPixel) || (!isLightBg && isLightPixel)) {
                    vertices.put(pixelCounter, false);
                }
            }
        }
    }

    /**
     * Method, that transforms rgba-image to rgb-image and combines initial image with background;
     */
    private GImage alphaBlend(GImage image) {

        int[][] RGBAtoRGBImage = image.getPixelArray();

        boolean isTransparentBg = GImage.getAlpha(RGBAtoRGBImage[0][0]) == 0;

        for (int y = 0; y < RGBAtoRGBImage.length; y++) {
            for (int x = 0; x < RGBAtoRGBImage[y].length; x++) {
                int pixel = RGBAtoRGBImage[y][x];

                int opacity = GImage.getAlpha(pixel) / 255;
                int red = GImage.getRed(pixel);
                int green = GImage.getGreen(pixel);
                int blue = GImage.getBlue(pixel);

                int alpha = (1 - opacity) * 255;
                int newRed, newGreen, newBlue;

                if (isTransparentBg && opacity == 1) {
                    newRed = 0;
                    newGreen = 0;
                    newBlue = 0;
                } else {
                    newRed = alpha + opacity * red;
                    newGreen = alpha + opacity * green;
                    newBlue = alpha + opacity * blue;
                }
                RGBAtoRGBImage[y][x] = GImage.createRGBPixel(newRed, newGreen, newBlue);
            }
        }
        return new GImage(RGBAtoRGBImage);
    }
}
