package com.shpp.p2p.cs.lzhukova.assignment17;

import com.shpp.p2p.cs.lzhukova.assignment17.assignment11.Assignment11Part1;
import com.shpp.p2p.cs.lzhukova.assignment17.assignment13.Assignment13Part1;
import com.shpp.p2p.cs.lzhukova.assignment17.assignment15.Assignment15Part1;


public class Assignment17Part1 {
    public static void main(String[] args) {
        String[] expression = {
                "-sin(a) - cos(-(-a + b)) + (-a)", "a = 30", "b = 90"
        };

        Assignment11Part1.main(expression);
        com.shpp.p2p.cs.lzhukova.assignment17.assignment11OnMyCollections.Assignment11Part1.main(expression);


        Assignment13Part1.main(new String[]{"src/com/shpp/p2p/cs/lzhukova/assignment17/assignment13/assets/3test.png"});
        com.shpp.p2p.cs.lzhukova.assignment17.assignment13OnMyCollections.Assignment13Part1.main(
                new String[]{"src/com/shpp/p2p/cs/lzhukova/assignment17/assignment13/assets/3test.png"});


        Assignment15Part1.main(new String[]{"src/com/shpp/p2p/cs/lzhukova/assignment17/assignment15/assets/happyhiphop.txt"});
        com.shpp.p2p.cs.lzhukova.assignment17.assignment15OnMyCollections.Assignment15Part1.main(new String[]{
                "src/com/shpp/p2p/cs/lzhukova/assignment17/assignment15/assets/happyhiphop.txt"});
    }
}
