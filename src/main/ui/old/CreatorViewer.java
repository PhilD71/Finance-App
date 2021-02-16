package ui.old;

import model.ChequingAccount;
import model.SaveType;
import model.SavingAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Displays a screen to create an account
 */
public class CreatorViewer extends JFrame implements ActionListener {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 60;
    private JPanel contentPane;
    JTextField balance = new JTextField("Type Balance Here");
    private JRadioButton savAccBut = new JRadioButton("Saving Account");
    private JRadioButton chqAccBut = new JRadioButton("Chequing Account");
    private JButton enter = new JButton("Enter");

    /**
     * MODIFIES: This
     * EFFECTS: creates an instance of this view
     */
    public CreatorViewer() {
        super("Personal Finance Tracker");
        setupContentPanel();
    }

    /**
     * MODIFIES: This
     * EFFECTS: Sets up JFrame
     */
    private void setupContentPanel() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(makeContentPane());
        setSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
        setResizable(false);
        pack();
    }

    /**
     * EFFECTS: Returns a JPanel with content for entire frame
     * @return JPanel
     */
    private JPanel makeContentPane() {
        contentPane = new JPanel();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(displayButtons());
        panel.add(makeTextPanel());
        contentPane.add(panel);
        contentPane.setVisible(true);
        return contentPane;
    }

    /**
     * EFFECTS: Returns a JPanel with a text field and a button for inputting value
     * @return JPanel
     */
    private JPanel makeTextPanel() {
        JPanel panel = new JPanel();
        balance.addActionListener(this);
        enter.addActionListener(this);
        enter.setActionCommand("enter");
        panel.add(balance);
        panel.add(enter);
        return panel;
    }

    /**
     * EFFECTS: Creates a panel with 2 radio buttons to select account type
     * @return: JPanel
     */
    private JPanel displayButtons() {
        JPanel panel = new JPanel(new FlowLayout());

        //savAccBut.setActionCommand("sav");
        savAccBut.addActionListener(this);
        //chqAccBut.setActionCommand("chq");
        chqAccBut.addActionListener(this);
        panel.add(savAccBut);
        panel.add(chqAccBut);
        return panel;
    }

    /**
     * EFFECTS: Creates a new controller with a new account of type selected with value inputted. This happens
     * when enter is clicked.
     * @param e enter button action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("enter")) {
            if (savAccBut.isSelected()) {
                selectAcc("sav");
            } else if (chqAccBut.isSelected()) {
                selectAcc("chq");
            }
        }
    }

    /**
     * REQUIRES: msg to be an action command
     * EFFECTS: Creates new account and new controller based on input action command.
     * If invalid inputs are detected, prompts user to re-input
     * @param msg action command
     */
    private void selectAcc(String msg) {
        switch (msg) {
            case "sav":
                try {
                    new SavingAccountController(new SavingAccount(Integer.parseInt(balance.getText()),
                            SaveType.SAVING));
                    dispose();
                } catch (NumberFormatException ex) {
                    new StartupErrorPopup("You didn't input a number", DISPOSE_ON_CLOSE);
                }
                break;
            case "chq":
                try {
                    new ChequingAccountController(new ChequingAccount(Integer.parseInt(balance.getText()),
                            SaveType.CHEQUING));
                    dispose();
                } catch (NumberFormatException ex) {
                    new StartupErrorPopup("You didn't input a number", DISPOSE_ON_CLOSE);
                }
                break;
        }
    }
}
