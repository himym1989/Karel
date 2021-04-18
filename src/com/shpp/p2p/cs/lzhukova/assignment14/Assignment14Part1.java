package com.shpp.p2p.cs.lzhukova.assignment14;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Assignment14Part1 {

    private static final String IN_FILE = "src/com/shpp/p2p/cs/lzhukova/assignment14/assets/d.txt";
    private static final String OUT_FILE = "src/com/shpp/p2p/cs/lzhukova/assignment14/assets/d.txt.par";

    public static void main(String[] args) {
        Compressor compressor = new Compressor();
        try {
            System.out.println("Start archive");
            FileInputStream fin = new FileInputStream(IN_FILE);

            long startArchiveTime = System.nanoTime();

            byte [] result = compressor.archive(fin.readAllBytes());
            long elapsedArchiveTime = System.nanoTime() - startArchiveTime;
            double elapsedTimeInSecond = (double) elapsedArchiveTime / 1_000_000_000;

            System.out.println("archive elapsed time " + elapsedTimeInSecond + " s");

            FileOutputStream fot = new FileOutputStream(OUT_FILE);
            fot.write(result);


            FileInputStream fin_a = new FileInputStream(OUT_FILE);
            byte[] result_a = compressor.unarchive(fin_a.readAllBytes());
            FileOutputStream fot_a = new FileOutputStream("src/com/shpp/p2p/cs/lzhukova/assignment14/assets/result.txt");
            fot_a.write(result_a);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
