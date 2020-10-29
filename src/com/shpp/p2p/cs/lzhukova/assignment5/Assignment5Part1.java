package com.shpp.p2p.cs.lzhukova.assignment5;

import com.shpp.cs.a.console.TextProgram;

public class Assignment5Part1 extends TextProgram {
    private final int[] vowels = {'a', 'e', 'i', 'o', 'u', 'y'};


    public void run() {
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */
        println("Unity" + " - " + syllablesIn("Unity") + " syllable(s)");
        println("Unite" + " - " + syllablesIn("Unite") + " syllable(s)");
        println("Growth" + " - " + syllablesIn("Growth") + " syllable(s)");
        println("Possibilities" + " - " + syllablesIn("Possibilities") + " syllable(s)");
        println("Nimble" + " - " + syllablesIn("Nimble") + " syllable(s)");
        println("Me" + " - " + syllablesIn("Me") + " syllable(s)");
        println("Beautiful" + " - " + syllablesIn("Beautiful") + " syllable(s)");
        println("Manatee" + " - " + syllablesIn("Manatee") + " syllable(s)");
        println("India" + " - " + syllablesIn("India") + " syllable(s)");


        while (true) {
            String word = readLine("Enter a single word: ");
            println("Unity" + " - " + syllablesIn("Unity") + " syllables");

            println("  Syllable count: " + syllablesIn(word));
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

        if (isVowel(word.charAt(0))) {
            syllables += 1;
        }

        for (int i = 1; i < length; i++) {
            if (isVowel(word.charAt(i)) && !isVowel(word.charAt(i - 1))) {
                syllables += 1;
            }
        }

        if (word.endsWith("e") && length > 3) {
            syllables -= 1;
        }

        return syllables;
    }

    private boolean isVowel(char c) {
        for (int vowel : vowels) {
            if (vowel == c) {
                return true;
            }
        }
        return false;
    }
}