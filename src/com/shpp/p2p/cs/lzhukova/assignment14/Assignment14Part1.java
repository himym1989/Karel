package com.shpp.p2p.cs.lzhukova.assignment14;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * This program implements compressor and decompressor of files. It works with files of different types.
 * if you don't want to use default archive extension "par" (for archived files) and "uar"(for unarchived files),
 * please use flags as a first argument:
 * - a (for archiving). Then please pass a file for archiving as a second argument and a file, that will
 * contain compressed info of the first file as a third argument, for example: "-a" "unicorn.png" "unicorn.archived";
 * - u (for unarchiving). Then please pass a file for unarchiving as a second argument ana a file, that will contain
 * uncompressed info of the first file as a third argument, for example: "-u" "unicorn.archived" "unicorn.unarchived";
 */

public class Assignment14Part1 {
    private static final String PATH_TO_FILE = "src/com/shpp/p2p/cs/lzhukova/assignment14/assets/";

    /**
     * default extension of archived files;
     */
    private static final String ARCHIVE_EXTENSION = ".par";
    /**
     * default extension of files after unarchiving;
     */
    private static final String UNARCHIVE_EXTENSION = ".uar";

    private static final String DEFAULT_IN_FILE = "test.txt";
    private static final String DEFAULT_OUT_FILE = "test.txt.par";

    private static final String FLAG_ARCHIVE = "-a";
    private static final String FLAG_UNARCHIVE = "-u";

    public static void main(String[] args) {
        // String, that will contain name of the file for archiving;
        String forArchiveFile = "";
        // String, that will contain name of the archived file;
        String archivedFile = "";
        // Sting, that will contain name of the file after unarchiving;
        String uarFile = "";

        // variable, that defines goal of the program: true - to archive, false - to unarchive;
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

            // Names of in- and out-files depends on the goal of program: archiving or unarchiving;
            String inFile = toArchive ? PATH_TO_FILE + forArchiveFile : PATH_TO_FILE + archivedFile;
            String outFile = toArchive ? PATH_TO_FILE + archivedFile : PATH_TO_FILE + uarFile;

            FileInputStream fin = new FileInputStream(inFile);

            // elapsed running time of the JVM in nanoseconds;
            long startArchiveTime = System.nanoTime();

            // the content of the result byte array depends also on the purpose of the program: to archive or unarchive;
            // different methods are called depends on the program's goal;
            byte[] result = toArchive ? compressor.archive(fin.readAllBytes()) : compressor.unarchive(fin.readAllBytes());
            long elapsedArchiveTime = System.nanoTime() - startArchiveTime;
            // elapsed time in seconds;
            double elapsedTimeInSecond = (double) elapsedArchiveTime / 1_000_000_000;


            FileOutputStream fot = new FileOutputStream(outFile);
            // writing to the result file
            fot.write(result);

            // sizes of in- and out-files;
            long inFileSize = new File(inFile).length();
            long outFileSize = new File(outFile).length();

            System.out.println();
            System.out.println("input file: " + inFile);
            System.out.println("output file: " + outFile);
            System.out.println("input file size: " + inFileSize + " bytes");
            System.out.println("output file size: " + outFileSize + " bytes");
            System.out.println("time taken: " + elapsedTimeInSecond + "s");
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
