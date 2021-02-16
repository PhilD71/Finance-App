package ui.old;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A window to select category of transaction you would like sorted
 */
public class CategorySelector extends JFrame implements ActionListener {

    private Controller controller;
    private JButton enter;
    private JTextField text;

    /**
     * MODIFIES: This
     * EFFECTS: Creates a category selector window
     * @param controller account controller needed to send actions to
     */
    public CategorySelector(Controller controller) {
        super("Personal Finance Tracker");
        this.controller = controller;
        setupFrame();
        add(displayTopWindow());
        add(displayBotWindow());
        pack();
    }

    /**
     * MODIFIES: This
     * EFFECTS: Sets up frame with panels and close details
     */
    private void setupFrame() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        setVisible(true);
        setResizable(false);
    }

    /**
     * EFFECTS: Creates a panel with text
     * @return JPanel
     */
    private JPanel displayTopWindow() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter the category you want to retrieve");
        label.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(label);
        return panel;
    }

    /**
     * EFFECTS: Creates a panel with a button and a text field for input
     * @return JPanel
     */
    private JPanel displayBotWindow() {
        JPanel panel = new JPanel();
        text = new JTextField("category", 25);
        text.setHorizontalAlignment(SwingConstants.LEFT);
        text.addActionListener(this);
        panel.add(text);
        enter = new JButton("Enter");
        enter.setActionCommand("enter");
        enter.addActionListener(this);
        panel.add(enter);
        return panel;
    }

    /**
     * MODIFIES: Controller
     * EFFECTS: When the button is pressed, the string in the text field gets sent to the controller
     * @param e button press event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String catString = text.getText();
        try {
            controller.setCategory(catString);
            controller.closeWindows();
            controller.viewCategory();
        } catch (Exception exp) {
            new StartupErrorPopup("Error", WindowConstants.DISPOSE_ON_CLOSE);
        }
    }


}
