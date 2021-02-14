package ui;

import model.exceptions.IllegalWithdrawalException;
import model.exceptions.TransactionException;
import ui.exception.UiException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A window for creating a transaction
 */
public class TransactionCreator extends JFrame implements ActionListener {
    private Controller controller;
    private JPanel contentPane;
    private JTextField value;
    private JTextField date;
    private JRadioButton typeCard;
    private JRadioButton typeChq;
    private JRadioButton typeTransf;
    private JRadioButton typeCash;
    private JTextField category;
    private JButton confirm;

    /**
     * MODIFIES: This
     * EFFECTS: Creates an instance of TransactionCreator
     * @param controller controller
     */
    public TransactionCreator(Controller controller) {
        super("Personal Finance Tracker");
        this.controller = controller;
        setupContentPanel();

    }

    /**
     * MODIFIES: This
     * EFFECTS: Sets up frame
     */
    private void setupContentPanel() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(makeContentPanel());
        setVisible(true);
        setResizable(false);
        pack();
    }

    /**
     * EFFECTS: Returns a JPanel with 6 different panels stacked on top of each other, with complete transction
     * creating functionality
     *
     * @return: JPanel
     */
    private JPanel makeContentPanel() {
        contentPane = new JPanel();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(moneyPanel());
        panel.add(datePanel());
        panel.add(typePanel());
        panel.add(categoryPanel());
        panel.add(confirmButton());
        contentPane.add(panel);
        contentPane.setSize(WIDTH, HEIGHT);
        contentPane.setVisible(true);
        return contentPane;
    }

    /**
     * EFFECTS: Returns a JPanel with a text field for value
     *
     * @return JPanel
     */
    private JPanel moneyPanel() {
        JPanel panel = new JPanel();
        value = new JTextField("+/- amount", 15);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black), "Value"));
        panel.add(value);
        value.addActionListener(this);
        return panel;
    }

    /**
     * EFFECTS Returns a JPanel with a text field for date
     *
     * @return JPanel
     */
    private JPanel datePanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black), "Date"));
        date = new JTextField("yyyy-mm-dd", 15);
        date.addActionListener(this);
        panel.add(date);
        return panel;
    }

    /**
     * EFFECTS: Returns a Jpanel with 4 radio buttons for type
     *
     * @return JPanel
     */
    private JPanel typePanel() {
        JPanel panel = new JPanel();
        declareTypeButtons();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black), "Transaction Type"));
        panel.add(typeCard);
        panel.add(typeChq);
        panel.add(typeTransf);
        panel.add(typeCash);
        return panel;
    }

    /**
     * EFFECTS: Sets up all buttons needed in this frame
     */
    private void declareTypeButtons() {
        typeCard = new JRadioButton("Card");
        typeCard.addActionListener(this);
        typeChq = new JRadioButton("Cheque");
        typeCard.addActionListener(this);
        typeTransf = new JRadioButton("Transfer");
        typeTransf.addActionListener(this);
        typeCash = new JRadioButton("Cash");
        typeCash.addActionListener(this);
    }

    /**
     * EFFECTS: Sets up category panel with text field
     *
     * @return JPanel
     */
    private JPanel categoryPanel() {
        JPanel panel = new JPanel();
        category = new JTextField("category", 15);
        category.addActionListener(this);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black), "Category"));
        panel.add(category);
        return panel;
    }

    /**
     * EFFECTS: Creates a Panel with an enter button
     *
     * @return: JPanel
     */
    private JPanel confirmButton() {
        JPanel panel = new JPanel();
        confirm = new JButton("Confirm");
        confirm.addActionListener(this);
        confirm.setActionCommand("confirm");
        panel.add(confirm);
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
     * MODIFIES: controller
     * EFFECTS: Once confirm is pressed, the transaction is generated. If there is an error with the formatting of a
     * field, a popup is shown, and the user is prompted to try again.
     *
     * @param e enter button event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("confirm")) {
            String dateString = date.getText();
            String money = value.getText();
            String cat = category.getText();

            try {
                controller.processTransaction(dateString, money, checkRadioButton(), cat);
            } catch (UiException ex) {
                new StartupErrorPopup("Please select only one Type", DISPOSE_ON_CLOSE);
            } catch (IllegalWithdrawalException ex) {
                new StartupErrorPopup("Cannot take money out of this account", DISPOSE_ON_CLOSE);
            } catch (TransactionException ex) {
                new StartupErrorPopup("Insufficient Funds", DISPOSE_ON_CLOSE);
            } catch (Exception ex) {
                new StartupErrorPopup("Check text fields for formatting", DISPOSE_ON_CLOSE);
            }

        }
    }
}
