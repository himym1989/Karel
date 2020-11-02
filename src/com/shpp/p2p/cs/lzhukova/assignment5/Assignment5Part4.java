package com.shpp.p2p.cs.lzhukova.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Assignment5Part4 extends TextProgram {
    private static final String filename = "assets/deniro.csv";
    BufferedReader br;
    String separator = ",";

    String line = "";
    ArrayList<String> data;
    int columnIndex = 0;

    public void run() {
        try {
            br = new BufferedReader(new FileReader(filename));

            while ((line = br.readLine()) != null) {
                fieldsIn(line);
            }

            extractColumn(filename, columnIndex);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void extractColumn(String filename, int columnIndex) {

    }

    private ArrayList<String> fieldsIn(String line) {
        ArrayList<String> fields = new ArrayList<String>();

        int len = line.length();

        // "Ye, ar", "Score", "Title"
        // 1968,  86, "Greetings"

        StringBuffer buffer = new StringBuffer(100);
        boolean isInQuote = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '"') {
                if (isInQuote) {
                    isInQuote = false;
                } else {
                    isInQuote = true;
                }
                buffer.append(ch);
            } else if (ch == ',') {
                if(!isInQuote){
                    fields.add(buffer.toString());
                    buffer.delete(0, buffer.length());
                }
                else buffer.append(ch);

            } else {
                buffer.append(ch);
            }
            if (i == line.length()-1) {
                fields.add(buffer.toString());
                buffer.delete(0, buffer.length());
            }
        }

        println(fields.get(1));
        return fields;
    }
}
