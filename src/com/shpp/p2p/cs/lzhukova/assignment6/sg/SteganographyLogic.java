package com.shpp.p2p.cs.lzhukova.assignment6.sg;

import acm.graphics.GImage;

public class SteganographyLogic {

    /**
     * Given a GImage containing a hidden message, finds the hidden message
     * contained within it and returns a boolean array containing that message.
     * <p/>
     * A message has been hidden in the input image as follows.  For each pixel
     * in the image, if that pixel has a red component that is an even number,
     * the message value at that pixel is false.  If the red component is an odd
     * number, the message value at that pixel is true.
     *
     * @param source The image containing the hidden message.
     * @return The hidden message, expressed as a boolean array.
     */
    public static boolean[][] findMessage(GImage source) {
        int[][] pixelArr = source.getPixelArray();
        boolean[][] secretMessage = new boolean[pixelArr.length][pixelArr[0].length];
        for (int y = 0; y < pixelArr.length; y++) {
            for (int x = 0; x < pixelArr[y].length; x++) {
                secretMessage[y][x] = GImage.getRed(pixelArr[y][x]) % 2 != 0;
            }
        }
        return secretMessage;
    }

    /**
     * Hides the given message inside the specified image.
     * <p/>
     * The image will be given to you as a GImage of some size, and the message will
     * be specified as a boolean array of pixels, where each white pixel is denoted
     * false and each black pixel is denoted true.
     * <p/>
     * The message should be hidden in the image by adjusting the red channel of all
     * the pixels in the original image.  For each pixel in the original image, you
     * should make the red channel an even number if the message color is white at
     * that position, and odd otherwise.
     * <p/>
     * You can assume that the dimensions of the message and the image are the same.
     * <p/>
     *
     * @param message The message to hide.
     * @param source  The source image.
     * @return A GImage whose pixels have the message hidden within it.
     */
    public static GImage hideMessage(boolean[][] message, GImage source) {
        int[][] pixelArr = source.getPixelArray();

        for (int y = 0; y < pixelArr.length; y++) {
            for (int x = 0; x < pixelArr[y].length; x++) {
                int red = GImage.getRed(pixelArr[y][x]);
                boolean isRedEven = red % 2 == 0;
                boolean isBlack = message[y][x];

                if (!isBlack && !isRedEven) {
                    System.out.println(red);
                    red = red - 1;
                } else if (isBlack && isRedEven) {
                    red = red + 1;

                }

                pixelArr[y][x] = GImage.createRGBPixel(
                        red,
                        GImage.getGreen(pixelArr[y][x]),
                        GImage.getBlue(pixelArr[y][x])
                );
            }
        }

        return new GImage(pixelArr);
    }
}

