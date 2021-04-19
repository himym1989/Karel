package com.shpp.p2p.cs.lzhukova.assignment14;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Assignment14Part1 {
    private static final String pathToFile = "src/com/shpp/p2p/cs/lzhukova/assignment14/assets/";
    private static final String ARCHIVE_EXTENSION = ".par";
    private static final String UNARCHIVE_EXTENSION = ".uar";
    private static final String DEFAULT_IN_FILE = "test.txt";
    private static final String DEFAULT_OUT_FILE = "test.txt.par";
    private static final String FLAG_ARCHIVE = "-a";
    private static final String FLAG_UNARCHIVE = "-u";

    public static void main(String[] args) {
        String forArchiveFile = "";
        String archivedFile = "";
        String uarFile = "";

        boolean toArchive = false;

        if (args.length == 0) {
            forArchiveFile = DEFAULT_IN_FILE;
            archivedFile = DEFAULT_OUT_FILE;
            toArchive = true;
        } else if (args[0].equals(FLAG_ARCHIVE)) {
            forArchiveFile = args[1];
            archivedFile = args[2];
            toArchive = true;
        } else if (args[0].equals(FLAG_UNARCHIVE)) {
            archivedFile = args[1];
            uarFile = args[2];
        } else if (!extensionOf(args[0]).equals(ARCHIVE_EXTENSION)) {
            forArchiveFile = args[0];
            if (args.length > 1) {
                archivedFile = args[1];
            } else {
                archivedFile = args[0] + ARCHIVE_EXTENSION;
            }
            toArchive = true;
        } else if (extensionOf(args[0]).equals(ARCHIVE_EXTENSION)) {
            archivedFile = args[0];
            if (args.length > 1) {
                uarFile = args[1];
            } else {
                uarFile = args[0].replaceAll(ARCHIVE_EXTENSION, UNARCHIVE_EXTENSION);
            }
            toArchive = false;
        }

        try {
            Compressor compressor = new Compressor();
            String inFile = toArchive ? pathToFile + forArchiveFile : pathToFile + archivedFile;
            String outFile = toArchive ? pathToFile + archivedFile : pathToFile + uarFile;

            FileInputStream fin = new FileInputStream(inFile);
            long startArchiveTime = System.nanoTime();
            byte[] result = toArchive ? compressor.archive(fin.readAllBytes()) : compressor.unarchive(fin.readAllBytes());
            long elapsedArchiveTime = System.nanoTime() - startArchiveTime;
            double elapsedTimeInSecond = (double) elapsedArchiveTime / 1_000_000_000;

            FileOutputStream fot = new FileOutputStream(outFile);
            fot.write(result);

            long inFileSize = new File(inFile).length();
            long outFileSize = new File(outFile).length();

            System.out.println();
            System.out.println("input file: " + inFile);
            System.out.println("output file: " + outFile);
            System.out.println("input file size: " + inFileSize + " bytes");
            System.out.println("output file size: " + outFileSize + " bytes");
            System.out.println("time taken: " + elapsedTimeInSecond + " s");
            System.out.println(toArchive ? "coefficient of efficiency : " +
                    (double) outFileSize / inFileSize * 100 + " %" : "");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Method, that returns extension of the file;
     *
     * @param filename name of the file, that is going to be added to the window;
     * @return String, extension of the file;
     */
    private static String extensionOf(String filename) {
        int lastDot = filename.lastIndexOf(46);
        return lastDot == -1 ? "" : filename.substring(lastDot);

    }

}
