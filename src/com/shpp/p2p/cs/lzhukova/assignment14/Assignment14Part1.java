package com.shpp.p2p.cs.lzhukova.assignment14;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Assignment14Part1 {

    private static final String IN_FILE = "src/com/shpp/p2p/cs/lzhukova/assignment14/assets/test.txt";
    private static final String OUT_FILE = "src/com/shpp/p2p/cs/lzhukova/assignment14/assets/test.txt.par";

    public static void main(String[] args) {
        Compressor compressor = new Compressor();
        try {
            System.out.println("Start archive");
            FileInputStream fin = new FileInputStream(OUT_FILE);
//            byte [] result = compressor.archive(fin.readAllBytes());
            System.out.println("End archive");
            byte[] result = compressor.unarchive(fin.readAllBytes());
//            System.out.println(result.length);

            FileOutputStream fot = new FileOutputStream(OUT_FILE);
            fot.write(result);

//            System.out.println("Start unarchive");
//            byte [] originResult = compressor.archive(result);
//            System.out.println(Arrays.toString(originResult));
//            System.out.println("End unarchive");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
