package com.shpp.p2p.cs.lzhukova.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This program implements a CSV-parser. It reads the CSV-file
 * using the BufferedReader and save it to the ArrayList.
 */
public class Assignment5Part4 extends TextProgram {
    private static final String FILENAME = "assets/deniro.csv";
    private static final char SEPARATOR = ',';


    public void run() {
        extractColumn(FILENAME, 2);
    }


    /**
     * This method extracts a necessary column from the created
     * from CSV-file ArrayList/
     *
     * @param filename    - CSV-file.
     * @param columnIndex - int, number of existing column.
     */
    private void extractColumn(String filename, int columnIndex) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null) {
                ArrayList<String> fields = fieldsIn(line);
                println(fields.get(columnIndex));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method takes a line from the CSV-file and
     * write it to the ArrayList of the Strings.
     *
     * @param line - String, line of the CSV-file.
     * @return fields, String ArrayList filled with data from CSV-file.
     */
    private ArrayList<String> fieldsIn(String line) {
        ArrayList<String> fields = new ArrayList<>();

        StringBuilder buffer = new StringBuilder(100);
        boolean isInQuote = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"' || ch == '“' || ch == '”') {
                if (isInQuote) {
                    isInQuote = false;
                } else {
                    isInQuote = true;
                }
            } else if (ch == ',') {
                if (!isInQuote) {
                    fields.add(buffer.toString());
                    buffer.delete(0, buffer.length());
                } else buffer.append(ch);

            } else {
                buffer.append(ch);
            }
        }

        if (buffer.length() > 0) {
            fields.add(buffer.toString());
        }

        return fields;
    }
}
