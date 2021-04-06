package com.shpp.p2p.cs.lzhukova.assignment12test;


    /**
     * This class declares some constants, that are used for searching silhouettes at the picture.
     */
    public interface Constants {

        double MIN_VALUABLE_LUMINANCE = 0.5;

        int MIN_VALUABLE_ALPHA = 150;

        /**
         * This constant is like a filter for silhouettes.
         * A silhouette, that takes more than 0.1 percent from the picture
         * will be descried as a silhouette.
         */
        double MIN_SCALE = 0.001;

}
