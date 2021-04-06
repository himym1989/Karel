package com.shpp.p2p.cs.lzhukova.assignment12;

import acm.graphics.GImage;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * This class implements a program, that is used by user for downloading a picture
 * for later silhouettes count.
 * <p>
 * If you get "StackOverFlowError", please write "-Xss100m" to the VM-options in configuration.
 */

public class Assignment12 {

    private static final String currentPicture = "src/com/shpp/p2p/cs/lzhukova/assignment12/assets/materials.png";

    private static final String[] FILTERS = {".png", ".bmp", ".wbmp", ".jpg", ".jpeg"};


    public static void main(String[] args) {
        GImage image;
        String filePath;


        if (args != null && args.length > 0 && !args[0].equals("")) {
            filePath = Paths.get(args[0]).toAbsolutePath().toString();
        } else {
            filePath = Paths.get(currentPicture).toAbsolutePath().toString();
        }
        if (acceptFileType(new File(filePath))) {
            try {
                image = new GImage(filePath);
                FindSilhouettes f = new FindSilhouettes(image);
                System.out.println(f.printResult());
            } catch (Exception e) {
                System.err.println("Something goes wrong with your file or path to it. Check and try again!");
            }

        } else {
            System.err.println("Incorrect file type!");
            System.exit(-1);
        }

    }

    public static boolean acceptFileType(File filename) {
        return filename.isDirectory() || Arrays.asList(FILTERS).contains(extensionOf(filename));
    }

    private static String extensionOf(File filename) {
        int lastDot = filename.getName().lastIndexOf(46);
        return lastDot == -1 ? "" : filename.getName().substring(lastDot);
    }
}
