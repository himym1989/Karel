package com.shpp.p2p.cs.lzhukova.assignment13;

import acm.graphics.GImage;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;


public class Assignment13 {


    private static final String TEST_PICTURE = "src/com/shpp/p2p/cs/lzhukova/assignment13/assets/tst_65.png";

    private static final String[] FILTERS = {".png", ".jpg", ".jpeg"};


    public static void main(String[] args) {
        GImage image;
        String filePath;


        if (args != null && args.length > 0 && !args[0].equals("")) {
            filePath = Paths.get(args[0]).toAbsolutePath().toString();
        } else {
            filePath = Paths.get(TEST_PICTURE).toAbsolutePath().toString();
        }
        if (acceptFileType(new File(filePath))) {
            try {

                image = new GImage(filePath);

                FindSilhouettes f = new FindSilhouettes(image);
            } catch (Exception e) {
                e.printStackTrace();
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
