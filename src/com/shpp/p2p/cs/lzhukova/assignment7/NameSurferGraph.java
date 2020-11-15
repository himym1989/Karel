package com.shpp.p2p.cs.lzhukova.assignment7;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {

    ArrayList<NameSurferEntry> entries = new ArrayList<>();


    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
    }

    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        entries.clear();
        update();
    }

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        entries.add(entry);
    }

    /**
     * Check if the input value is already on the graph.
     * @return boolean
     */
    public boolean hasEntry(NameSurferEntry entry) {
        return entries.contains(entry);
    }
    /**
     * Rendering Entries, sitting in the loop.
     * Each entry should have its own color.
     */
    private void renderEntries() {

        // height of available vertical scale
        double verticalScale = getHeight() - 2 * GRAPH_MARGIN_SIZE;
        Color[] colors = getColors();

        for (NameSurferEntry entry : entries) {
            int entryColor = entries.indexOf(entry) % 4;

            for (int i = 0; i < NDECADES; i++) {

                boolean isLastDecade = i == NDECADES - 1;
                int nextRank = isLastDecade ? entry.getRank(i) : entry.getRank(i + 1);

                double thisRankPoint = verticalScale * entry.getRank(i) / MAX_RANK;
                double nextRankPoint = verticalScale * nextRank / MAX_RANK;

                final int decadeNum = getWidth() / NDECADES;
                final int bottomPoint = getHeight() - GRAPH_MARGIN_SIZE;

                // rendering a graph
                GLine graphLine = new GLine(
                        i * decadeNum,
                        entry.getRank(i) < 1 ? bottomPoint : thisRankPoint + GRAPH_MARGIN_SIZE,
                        isLastDecade ? i * decadeNum : (i + 1) * decadeNum,
                        nextRank < 1 ? bottomPoint : nextRankPoint + GRAPH_MARGIN_SIZE
                );
                graphLine.setColor(colors[entryColor]);
                add(graphLine);

                // rendering a point on the graph scales
                int scalePointWidth = 4;
                GRect scalePoint = new GRect(
                        i * decadeNum - scalePointWidth / 2f,
                        entry.getRank(i) < 1 ? bottomPoint : thisRankPoint + GRAPH_MARGIN_SIZE,
                        scalePointWidth, scalePointWidth
                );
                scalePoint.setColor(colors[entryColor]);
                scalePoint.setFilled(true);
                add(scalePoint);

                // rendering a label with name and popularity value
                GLabel name = new GLabel(entry.getName() + " " + entry.getRank(i));
                name.setFont("Verdana-9");
                name.setColor(colors[entryColor]);
                add(name, i * decadeNum, (entry.getRank(i) < 1 ? bottomPoint : thisRankPoint + GRAPH_MARGIN_SIZE));
            }
        }
    }

    /**
     * Method creates colors array for entries.
     *
     * @return array of colors.
     */
    private Color[] getColors() {
        return new Color[]{
                Color.BLUE,
                Color.RED,
                Color.MAGENTA,
                Color.BLACK
        };
    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        removeAll();
        renderGrid();
        renderEntries();
    }

    /**
     * Rendering a grid for the graph
     */
    private void renderGrid() {

        GLine horizontalTopLine = new GLine(
                0,
                GRAPH_MARGIN_SIZE,
                getWidth(),
                GRAPH_MARGIN_SIZE);
        add(horizontalTopLine);

        GLine horizontalBottomLine = new GLine(
                0,
                getHeight() - GRAPH_MARGIN_SIZE,
                getWidth(),
                getHeight() - GRAPH_MARGIN_SIZE);
        add(horizontalBottomLine);

        for (int i = 0; i < NDECADES; i++) {
            GLine verticalLine = new GLine(
                    i * (double) (getWidth() / NDECADES),
                    0,
                    i * (double) (getWidth() / NDECADES),
                    getHeight());
            add(verticalLine);

            String decadeValue = String.valueOf(START_DECADE + i * 10);
            GLabel decade = new GLabel(decadeValue);
            decade.setFont("Times New Roman-" + GRAPH_MARGIN_SIZE);
            add(decade, i * (double) (getWidth() / NDECADES), getHeight() - decade.getDescent());
        }

    }


    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}
