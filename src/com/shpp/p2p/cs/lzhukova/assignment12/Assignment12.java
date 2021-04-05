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

/**
 * This class implements a window program, that is used by user for downloading a picture
 * for later silhouettes count.
 * <p>
 * Default picture for test is located in the package "assets".
 * Please check the right path to this picture!!!
 * <p>
 * If you get "StackOverFlowError", please write "-Xss100m" to the VM-options in configuration.
 */

public class Assignment12 extends WindowProgram implements ComponentListener {

    private final String TEST_PICTURE = "src/com/shpp/p2p/cs/lzhukova/assignment12/assets/materials.png";

    private final String[] FILTERS = {".png", ".bmp", ".wbmp", ".jpg", ".jpeg"};

    public GImage displayImage;
    GImage image;
    private JButton chooseImage;
    private JButton countSilhouettes;
    private JFileChooser fileChooser;
    private JDialog dialog;
    private JLabel label;

    public void init() {
        fileChooser = new JFileChooser();

        chooseImage = new JButton("Choose Image");
        add(chooseImage, SOUTH);

        countSilhouettes = new JButton("Count Silhouettes");
        add(countSilhouettes, SOUTH);

        image = new GImage(TEST_PICTURE);
        setImage(image);

        addDialog();

        addActionListeners();
        this.addComponentListener(this);
    }


    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == chooseImage) {
            dialog.setVisible(false);
            displayImage = null;
            removeAll();
            this.chooseImage();
        }
        if (event.getSource() == countSilhouettes) {
            this.countSilhouettes();
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
            this.add(this.displayImage);
        }
        this.redrawAll();
    }

    private void redrawAll() {
        if (this.displayImage != null) {
            this.displayImage.setBounds(0.0D, 0.0D, this.getWidth(),
                    this.getHeight());
        }

    }

    private void countSilhouettes() {
        if (displayImage != null) {
            try {
                FindSilhouettes findSilhouettes = new FindSilhouettes(displayImage);
                showMessage(findSilhouettes.printResult());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showMessage("You haven't chosen any image!");
        }
    }


    /**
     * This method is called if the input name is null or already exists.
     */
    private void addDialog() {
        dialog = new JDialog();
        dialog.setSize(300, 100);
        int x = (getWidth() - dialog.getWidth()) / 2;
        int y = (getHeight() - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);

        label = new JLabel("msg", SwingConstants.CENTER);

        dialog.add(label);

    }

    /**
     * Method set text to the label and makes dialog visible.
     *
     * @param msg, string, that will appear in the  dialog.
     */
    private void showMessage(String msg) {
        label.setText(msg);
        dialog.setVisible(true);
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
