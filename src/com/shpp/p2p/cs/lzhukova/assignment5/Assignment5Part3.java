package com.shpp.p2p.cs.lzhukova.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This program implements the so-called road game.
 * It takes three letters from the user and then looks for
 * words in the dictionary, that can be made from this letters.
 * But the letters in the word must be in the same order,
 * as the user input them.
 */
public class Assignment5Part3 extends TextProgram {
    private static final String WORDS_FILE = "assets/dictionary.txt";
    private final ArrayList<String> dictionary = new ArrayList<>();
    private String letters;

    public void run() {
        initDictionary();
        while (true) {
            letters = readLine("put 3 letters from the car sign");
            letters = letters.toLowerCase();
            printMatches();
        }
    }

    /**
     * This method create a dictionary, using BufferedReader.
     */
    private void initDictionary() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(WORDS_FILE));
            while (true) {
                String word = br.readLine();
                if (word == null) {
                    break;
                }
                dictionary.add(word);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method looks for matches and print them.
     */
    private void printMatches() {
        for (String word : dictionary) {
            int firstLetter = word.indexOf(letters.charAt(0));
            if (firstLetter < 0) continue;
            int secondLetter = word.indexOf(letters.charAt(1), firstLetter + 1);
            if (secondLetter < 0) continue;
            int thirdLetter = word.indexOf(letters.charAt(2), secondLetter + 1);
            if (thirdLetter < 0) continue;
            println(word);
        }
    }
}
