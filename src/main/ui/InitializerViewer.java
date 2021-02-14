package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The startup viewer for the application
 */
public class InitializerViewer extends JFrame implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 100;
    private JLabel titleLabel = new JLabel("Would you like to create or load an account?");
    private JButton loadButton = new JButton("Load");
    private JButton createButton = new JButton("Create");

    /**
     * MODIFIES: This
     * EFFECTS: creates a new initializer viewer
     */
    public InitializerViewer() {
        super("Personal Finance Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        setSize(new Dimension(WIDTH, HEIGHT));
        addPanels();
        setVisible(true);
        setResizable(false);
    }

    /**
     * MODIFIES: This
     * EFFECTS: adds top and bottom panels to frame
     */
    private void addPanels() {
        add(createTopPanel());
        add(createBottomPanel());
    }

    /**
     * EFFECTS: Creates a JPanel with text information for user
     * @return: Jpanel
     */
    private JPanel createTopPanel() {
        JPanel innerPanelTop = new JPanel();
        innerPanelTop.setLayout(new GridLayout(1, 1));
        innerPanelTop.setSize(new Dimension(300, 50));
        innerPanelTop.setVisible(true);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        innerPanelTop.add(titleLabel);
        return innerPanelTop;
    }

    /**
     * EFFECTS: Creates a JPanel with two buttons, load and create
     * @return: JPanel
     */
    private JPanel createBottomPanel() {
        JPanel innerPanelBot = new JPanel();
        innerPanelBot.setLayout(new FlowLayout(FlowLayout.CENTER));
        innerPanelBot.setPreferredSize(new Dimension(300, 50));
        innerPanelBot.setVisible(true);
        loadButton.setActionCommand("load");
        loadButton.addActionListener(this);
        createButton.setActionCommand("create");
        createButton.addActionListener(this);
        innerPanelBot.add(loadButton);
        innerPanelBot.add(createButton);
        return innerPanelBot;
    }

    /**
     * EFFECTS: Based on what button is selected, account is loaded in from memory, or user is taken to
     * account setup screen
     * @param e button event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case ("load"):
                new LoaderController();
                dispose();
                break;
            case ("create"):
                new CreatorViewer();
                dispose();
                break;
        }
    }
}
