package ui.old;

import ui.exception.UiException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A window to select type of transaction you would like sorted
 */
public class TypeSelector extends JFrame implements ActionListener {
    private Controller controller;
    private JButton enter;
    private JPanel contentPanel;
    private JRadioButton typeCard;
    private JRadioButton typeChq;
    private JRadioButton typeTransf;
    private JRadioButton typeCash;

    /**
     * MODIFIES: This
     * EFFECTS: Creates a category selector window
     * @param controller account controller needed to send actions to
     */
    public TypeSelector(Controller controller) {
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
     * EFFECTS: Creates a panel with 4 radio buttons and an enter button
     * @return JPanel
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
     * EFFECTS: Creates a panel with 4 radio buttons, one per type
     * @return JPanel
     */
    private JPanel createRadio() {
        JPanel panel = new JPanel();
        typeCard = new JRadioButton("Card");
        typeCard.addActionListener(this);
        typeChq = new JRadioButton("Cheque");
        typeCard.addActionListener(this);
        typeTransf = new JRadioButton("Transfer");
        typeTransf.addActionListener(this);
        typeCash = new JRadioButton("Cash");
        typeCash.addActionListener(this);
        panel.add(typeCard);
        panel.add(typeChq);
        panel.add(typeTransf);
        panel.add(typeCash);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black), "Type"));
        return panel;
    }

    /**
     * EFFECTS: If 0 or more than one radio button is selected, error is thrown, otherwise, returns the type that
     * is associated with the single selected radio button
     * @return Type
     * @throws UiException if there is more or less than one button selected
     */
    private model.Type checkRadioButton() throws UiException {
        boolean card = typeCard.isSelected();
        boolean cash = typeCash.isSelected();
        boolean transf = typeTransf.isSelected();
        boolean chq = typeChq.isSelected();

        if (!(card ^ cash ^ transf ^ chq)) {
            throw new UiException();
        }
        if (card) {
            return model.Type.CARD;
        }
        if (cash) {
            return model.Type.CASH;
        }
        if (transf) {
            return model.Type.TRANSFER;
        } else {
            return model.Type.CHEQUE;
        }
    }

    /**
     * MODIFIES: Controller
     * EFFECTS: When enter is clicked, sets type of controller to type from radio button.
     * If error in input, prompts user to try again
     * @param e enter action button event
     */
    //TODO make it possible to select multiple types
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("enter")) {
            try {
                controller.setType(checkRadioButton());
                controller.closeWindows();
                controller.viewType();
            } catch (UiException uiException) {
                new StartupErrorPopup("Can't select multiple types!", WindowConstants.DISPOSE_ON_CLOSE);
            }
        }
    }
}
