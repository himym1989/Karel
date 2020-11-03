package com.shpp.p2p.cs.lzhukova.assignment5;

import com.shpp.cs.a.console.TextProgram;


/**
 * This program implements dividing English words into syllables.
 * It takes a word as a parameter and return syllables number.
 */
public class Assignment5Part1 extends TextProgram {
    private final int[] vowels = {'a', 'e', 'i', 'o', 'u', 'y'};


    public void run() {

        while (true) {
            String word = readLine("Enter a single word: ");
            if (word == "") {
                println("input a word, please!");
            } else {
                println("  Syllable count: " + syllablesIn(word));
            }
        }
    }

    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    private int syllablesIn(String word) {

        word = word.toLowerCase();

        int length = word.length();
        int syllables = 0;

        /* check of the first char in the word. */
        if (isVowel(word.charAt(0))) {
            syllables += 1;
        }

        for (int i = 1; i < length; i++) {
            if (isVowel(word.charAt(i)) && !isVowel(word.charAt(i - 1))) {
                syllables += 1;
            }
        }

        /* check of the last char in the word. */
        if (word.endsWith("e") && length > 3) {
            syllables -= 1;
        }

        return syllables;
    }

    /**
     * This method implements check if char is a vowel or not;
     *
     * @param c - char in the word;
     * @return boolean; true - if the char is vowel;
     */
    private boolean isVowel(char c) {
        for (int vowel : vowels) {
            if (vowel == c) {
                return true;
            }
        }
        return false;
    }
}