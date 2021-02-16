package ui.old;

import ui.exception.UiException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A window to select direction of transaction you would like sorted
 */
public class DirectionSelector extends JFrame implements ActionListener {
    private Controller controller;
    private JButton enter;
    private JPanel contentPanel;
    private JRadioButton incoming;
    private JRadioButton outgoing;

    /**
     * MODIFIES: This
     * EFFECTS: Creates a direction selector window
     * @param controller account controller needed to send actions to
     */
    public DirectionSelector(Controller controller) {
        super("Personal Finance Tracker");
        this.controller = controller;
        setupContentPanel();
    }

    /**
     * MODIFIES: This
     * EFFECTS: Sets up frame with panels and close details
     */
    private void setupContentPanel() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(makeContentPane());
        setVisible(true);
        setResizable(false);
        pack();
    }

    /**
     * EFFECTS: Makes a content panel with buttons and enter button
     * @return: JPanel
     */
    private JPanel makeContentPane() {
        contentPanel = new JPanel();
        JPanel panel = new JPanel();
        JPanel panelTwo = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(createRadio());
        enter = new JButton("enter");
        enter.setHorizontalAlignment(SwingConstants.CENTER);
        enter.setActionCommand("enter");
        enter.addActionListener(this);
        panelTwo.add(enter);
        panel.add(panelTwo);
        contentPanel.add(panel);
        return contentPanel;
    }

    /**
     * EFFECTS: Makes a panel with 2 radio buttons, one for deposits and one for withdrawls
     * @return: JPanel
     */
    private JPanel createRadio() {
        JPanel panel = new JPanel();
        incoming = new JRadioButton("Deposits");
        incoming.addActionListener(this);
        outgoing = new JRadioButton("Withdrawals");
        outgoing.addActionListener(this);
        panel.add(incoming);
        panel.add(outgoing);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black), "Direction"));
        return panel;
    }

    /**
     * EFFECTS: If both radio buttons are selected, throws UI Exception
     * otherwise, returns true if transaction is outgoing, returns false if transaction is incoming
     * @return boolean
     * @throws UiException thrown if zero or both buttons selected
     */
    private boolean checkRadioButton() throws UiException {
        boolean in = incoming.isSelected();
        boolean out = outgoing.isSelected();

        if (!(in ^ out)) {
            throw new UiException();
        }

        return out;
    }

    /**
     * MODIFIES: Controller
     * EFFECTS: When enter is pressed, it sends to controller which direction of transaction the user wants
     * to display. If more than one is selected, it prompts the user to try again
     * @param e Enter button press event
     */
    //TODO make it possible to select multiple types
    @Override
    public void actionPerformed(ActionEvent e) {
        ChequingAccountController chq = (ChequingAccountController) controller;
        if (e.getActionCommand().equals("enter")) {
            try {
                chq.setDirection(checkRadioButton());
                chq.closeWindows();
                chq.viewDirection();
            } catch (UiException uiException) {
                new StartupErrorPopup("Can't select multiple directions!", WindowConstants.DISPOSE_ON_CLOSE);
            }
        }
    }
}
