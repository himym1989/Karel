package com.shpp.p2p.cs.lzhukova.assignment7;

/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.Arrays;

public class NameSurferEntry implements NameSurferConstants {

    private final String name;
    private final int[] ranks;

    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        String[] dataValues = line.split(" ");
        name = dataValues[0];

        ranks = new int[dataValues.length - 1];
        for (int i = 0; i < ranks.length; i++) {
            ranks[i] = Integer.parseInt(dataValues[i + 1]);
        }
    }

    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decade) {
        return ranks[decade];
    }

    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    public String toString() {
        return name + " " + Arrays.toString(ranks);
    }
}

