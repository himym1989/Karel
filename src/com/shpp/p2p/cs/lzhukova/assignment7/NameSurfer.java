package com.shpp.p2p.cs.lzhukova.assignment7;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import com.shpp.cs.a.simple.SimpleProgram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {

    private JTextField nameField;
    private JButton graphButton;
    private JButton clearButton;
    private NameSurferGraph graph;

    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */

    public void init() {
        add(new JLabel("Name: "), NORTH);

        nameField = new JTextField(12);
        add(nameField, NORTH);

        nameField.setActionCommand("EnterPressed");
        nameField.addActionListener(this);

        graphButton = new JButton("Graph");
        add(graphButton, NORTH);

        clearButton = new JButton("Clear");
        add(clearButton, NORTH);

        graph = new NameSurferGraph();
        add(graph);

        addActionListeners();
    }

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String nameValue = nameField.getText();

        if (cmd.equals("EnterPressed") || e.getSource() == graphButton) {
            try {
                NameSurferDataBase base = new NameSurferDataBase(NAMES_DATA_FILE);
                NameSurferEntry entry = base.findEntry(nameValue);

                if (entry == null) {
                    System.out.println("oops");
                } else {
                    graph.addEntry(entry);
                    graph.update();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        if (e.getSource() == clearButton) {
            graph.clear();
        }

    }
}
