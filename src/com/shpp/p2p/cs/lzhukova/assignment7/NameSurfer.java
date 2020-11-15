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
    private NameSurferDataBase base;

    private JDialog errorDialog;
    private JLabel errorLabel;

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

        try {
            base = new NameSurferDataBase(NAMES_DATA_FILE);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        addErrorDialog();
        addActionListeners();
    }


    /**
     * This method is called if the input name is null or already exists.
     */
    private void addErrorDialog() {
        errorDialog = new JDialog();
        errorDialog.setSize(300, 100);
        final int x = (getWidth() - errorDialog.getWidth()) / 2;
        final int y = (getHeight() - errorDialog.getHeight()) / 2;
        errorDialog.setLocation(x, y);

        errorLabel = new JLabel("msg", SwingConstants.CENTER);
        errorDialog.add(errorLabel);
    }


    /**
     * Method set text to the error label and makes error dialog visible.
     *
     * @param msg, string, that will appear in the error dialog.
     */
    private void showError(String msg) {
        errorLabel.setText(msg);
        errorDialog.setVisible(true);
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

            NameSurferEntry entry = base.findEntry(nameValue);

            if (entry == null) {
                showError("No name found!");
            } else if (graph.hasEntry(entry)) {
                showError("This name is already on the graph!");
            } else {
                graph.addEntry(entry);
                graph.update();
            }
        }

        if (e.getSource() == clearButton) {
            graph.clear();
        }
    }
}