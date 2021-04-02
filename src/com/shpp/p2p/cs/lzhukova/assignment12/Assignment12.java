package com.shpp.p2p.cs.lzhukova.assignment12;

import acm.graphics.GImage;
import acm.util.ErrorException;
import com.shpp.cs.a.graphics.WindowProgram;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.Arrays;


public class Assignment12 extends WindowProgram implements ComponentListener {
    private final String[] FILTERS = {".png", ".bmp", ".wbmp", ".jpg", ".gif", ".jpeg"};
    GImage image;
    public GImage displayImage;
    private JButton chooseImage;
    private JButton countSilhouettes;
    private JFileChooser fileChooser;
    private JDialog errorDialog;
    private JLabel errorLabel;

    public void init() {
        fileChooser = new JFileChooser();

        chooseImage = new JButton("Choose Image");
        add(chooseImage, SOUTH);

        countSilhouettes = new JButton("Count Silhouettes");
        add(countSilhouettes, SOUTH);

        addErrorDialog();

        addActionListeners();
        this.addComponentListener(this);
    }


    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == chooseImage) {
            this.chooseImage();
        }
        if (event.getSource() == countSilhouettes) {
            this.countSilhouettes(image);
        }

    }

    private void chooseImage() {
        fileChooser.setDialogTitle("Choose an image");
        fileChooser.setFileFilter(new FileFilter() {
            public boolean accept(File filename) {
                return filename.isDirectory() || Arrays.asList(FILTERS).contains(extensionOf(filename));
            }

            public String getDescription() {
                return "Image files";
            }
        });
        if (fileChooser.showOpenDialog(this) == 0) {
            try {
                this.image = new GImage(fileChooser.getSelectedFile().getAbsolutePath());
                this.setImage(this.image);
            } catch (ErrorException var3) {
                this.getDialog().showErrorMessage("Error loading file: " + var3.getMessage());
            }
        }

    }

    private String extensionOf(File filename) {
        int lastDot = filename.getName().lastIndexOf(46);
        return lastDot == -1 ? "" : filename.getName().substring(lastDot);
    }

    private void setImage(GImage image) {
        this.displayImage = image;
        if (image != null) {
            this.displayImage.setBounds(0.0D, 0.0D, 0.0D, 0.0D);
            this.add(this.displayImage);
        }

        this.redrawAll();
    }

    private void redrawAll() {
        if (this.displayImage != null) {
            this.displayImage.setBounds(0.0D, 0.0D, (double) this.getWidth(),
                    this.getHeight());
        }

    }

    private void countSilhouettes(GImage image) {
        if (displayImage != null) {
            try {
                FindingSilhouettes f = new FindingSilhouettes(displayImage);
//                f.getVertices(); // Todo here must be find method
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showError("You haven't chosen any image!");
        }
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

    public void componentResized(ComponentEvent arg0) {
        this.redrawAll();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

}
