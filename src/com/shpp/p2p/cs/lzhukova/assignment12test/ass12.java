package com.shpp.p2p.cs.lzhukova.assignment12test;


import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ass12 {
    public static void main(String[] args) throws IOException {
        File file = new File("src/com/shpp/p2p/cs/lzhukova/assignment12/assets/materials.png");
        BufferedImage image = ImageIO.read(file);

        final int w = image.getWidth();
        final int h = image.getHeight();

        BufferedImage scaledImage = new BufferedImage(w / 4, h / 4, BufferedImage.TYPE_INT_ARGB);

        final AffineTransform at = AffineTransform.getScaleInstance(0.25, 0.25);
        final AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);

        scaledImage = ato.filter(image, scaledImage);

        FindSil f = new FindSil(scaledImage);

        System.out.println(f.printResult());
    }
}
