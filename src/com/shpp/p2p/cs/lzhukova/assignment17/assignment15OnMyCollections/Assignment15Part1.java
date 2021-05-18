package com.shpp.p2p.cs.lzhukova.assignment17.assignment15OnMyCollections;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * This program implements compressor and decompressor of files. It works with files of different types.
 * if you don't want to use default archive extension "par" (for archived files) and "uar"(for unarchived files),
 * please use flags as a first argument:
 * - a (for archiving). Then please pass a file for archiving as a second argument and a file, that will
 * contain compressed info of the first file as a third argument, for example: "-a" "unicorn.png" "unicorn.archived";
 * - u (for unarchiving). Then please pass a file for unarchiving as a second argument ana a file, that will contain
 * uncompressed info of the first file as a third argument, for example: "-u" "unicorn.archived" "unicorn.unarchived";
 */

public class Assignment15Part1 {
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static final String ASSETS_PATH = "";

    /**
     * default extension of archived files;
     */
    private static final String ARCHIVE_EXTENSION = ".par";
    /**
     * default extension of files after unarchiving;
     */
    private static final String UNARCHIVE_EXTENSION = ".uar";

    // 1.txt.par 1new.txt
    private static final String DEFAULT_IN_FILE = "medium.txt";
    private static final String DEFAULT_OUT_FILE = "medium.txt.par";

    private static final String FLAG_ARCHIVE = "-a";
    private static final String FLAG_UNARCHIVE = "-u";

    public static void main(String[] args) {
        System.out.println(ANSI_YELLOW + "Start Huffman compressing on my collections" + ANSI_RESET);

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
            // Names of in- and out-files depends on the goal of program: archiving or unarchiving;
            String inFile = ASSETS_PATH + (toArchive ? forArchiveFile : archivedFile);
            String outFile = ASSETS_PATH + (toArchive ? archivedFile : uarFile);

            // elapsed running time of the JVM in nanoseconds;
            long startArchiveTime = System.currentTimeMillis();

            Path filePath = Paths.get(inFile);
            if (!Files.exists(filePath)) {
                throw new Exception("File `" + filePath + "` not found.");
            }

            // the content of the result byte array depends also on the purpose of the program: to archive or unarchive;
            // different methods are called depends on the program's goal;

            byte[] bytes = Files.readAllBytes(filePath);
            Compressor compressor = new Compressor(bytes);
            Decompressor decompressor = new Decompressor(bytes);
            byte[] result = toArchive ? compressor.compress() : decompressor.decompress();


            FileOutputStream fot = new FileOutputStream(outFile);
//            // writing to the result file
            fot.write(result);

            // elapsed time in seconds:
            // converting nano-seconds to seconds dividing by 1_000_000_000;
            long elapsedArchiveTime = System.currentTimeMillis();

            // sizes of in- and out-files;
            long inFileSize = new File(inFile).length();
            long outFileSize = new File(outFile).length();

//            System.out.println(ANSI_YELLOW + "input file: " + ANSI_RESET + inFile);
//            System.out.println(ANSI_YELLOW + "output file: " + ANSI_RESET + outFile);
            System.out.println(ANSI_YELLOW + "input file size: " + ANSI_RESET + inFileSize + " bytes");
            System.out.println(ANSI_YELLOW + "output file size: " + ANSI_RESET + outFileSize + " bytes");
            System.out.println(ANSI_YELLOW + "time taken: " + ANSI_RESET + (elapsedArchiveTime - startArchiveTime) + "ms");
//            System.out.println(toArchive ? ANSI_YELLOW + "coefficient of efficiency : " + ANSI_RESET +
//                    (double) outFileSize / inFileSize * 100 + " %" : "");

        } catch (Throwable e) {
            System.out.println(ANSI_RED + e.getMessage());
        }
    }

    /**
     * Method, that returns extension of the file;
     *
     * @param filename name of the file, that is going to be added to the window;
     * @return String, extension of the file;
     */
    private static String extensionOf(String filename) {
        int lastDot = filename.lastIndexOf('.');
        return lastDot == -1 ? "" : filename.substring(lastDot);
    }
}
