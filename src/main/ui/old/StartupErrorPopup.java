package ui.old;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A basic error popup window
 */
public class StartupErrorPopup extends JFrame {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 60;
    private JLabel titleLabel;

    /**
     * Modifies: this
     * Effects: creates a window with a specified error message
     * @param message error message to be displayed
     * @param closeStatus Chooses if program is terminated or if taken back to previous screen
     */
    public StartupErrorPopup(String message, int closeStatus) {
        super("WARNING!");
        titleLabel = new JLabel(message);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        setDefaultCloseOperation(closeStatus);
        setLayout(new FlowLayout());
        setSize(new Dimension(WIDTH, HEIGHT));
        add(titleLabel);
        setVisible(true);
        setResizable(false);
        pack();
    }
}
