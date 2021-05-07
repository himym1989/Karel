package com.shpp.p2p.cs.lzhukova.assignment16;

import java.util.ArrayList;

public class Assignment16Part1 {
    public static void main(String[] args) throws Exception {
        MyArrayList<String> myArraylist = new MyArrayList<>();
        myArraylist.add("1");
        myArraylist.add("2");
        myArraylist.add("4");
        myArraylist.add("5");
        myArraylist.add("6");
        myArraylist.add("8");
        myArraylist.add("9");
        myArraylist.add("10");
        myArraylist.add("11");
        myArraylist.add("12");
        myArraylist.add("13");
        myArraylist.add("14");
        myArraylist.add(2, "3");
        myArraylist.add(6, "7");
        myArraylist.get(17);
//        myArraylist.add(15, "16");
//        myArraylist.add(16, "17");

        ArrayList<String> arr = new ArrayList<>();
        arr.add("hi");
//        arr.add(2, "Hello");
//        System.out.println(arr.get(1));
//        System.out.println(myArraylist.get(8));
        System.out.println(myArraylist);
        System.out.println(myArraylist.getLength());
        myArraylist.removeAll();
        System.out.println(myArraylist);
        System.out.println(myArraylist.getLength());
//        System.out.println(myArraylist.get(18));
    }
}
