package ui;

import model.SavingAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoalSetter extends JFrame implements ActionListener {
    private SavingAccountController controller;
    private JButton enter;
    private JTextField text;

    public GoalSetter(SavingAccountController controller) {
        super("Goal Setter");
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
     *
     * @return JPanel
     */
    private JPanel displayTopWindow() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter the value you'd like to set as your goal");
        label.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(label);
        return panel;
    }

    /**
     * EFFECTS: Creates a panel with a button and a text field for input
     *
     * @return JPanel
     */
    private JPanel displayBotWindow() {
        JPanel panel = new JPanel();
        text = new JTextField("amount", 25);
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
     *
     * @param e button press event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String amountString = text.getText();
        try {
            controller.setGoal(Integer.parseInt(amountString));
            controller.closeWindows();
            controller.viewDefault();
        } catch (Exception exp) {
            new StartupErrorPopup("Please input a number", WindowConstants.DISPOSE_ON_CLOSE);
        }
    }
}
