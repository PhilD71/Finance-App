package ui.old;

import model.Transaction;

import persistence.Saver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.FileNotFoundException;

import java.util.List;

/**
 * A viewer for a chequing account
 */
public class ChequingAccountViewer extends AccountViewer {

    /**
     * MODIFIES: This
     * EFFECTS: Constructs an ChequingAccountViewer
     *
     * @param controller controller for viewer
     */
    public ChequingAccountViewer(ChequingAccountController controller) {
        super(controller);
        setTitle("Chequing Account Tracker");
    }

    /**
     * MODIFIES: This
     * EFFECTS: Constructs an ChequingAccountViewer
     *
     * @param controller      controller for viewer
     * @param transactionList list of transactions
     */
    public ChequingAccountViewer(ChequingAccountController controller, List<Transaction> transactionList) {
        super(controller, transactionList);
        setTitle("Chequing Account Tracker");
    }

    /**
     * EFFECTS: creates panel with button to add transactions
     *
     * @return JPanel with transaction adder button
     */
    protected JPanel displayTransactionAdder() {
        JPanel panel = new JPanel();
        addTrans = new JButton("Add Transaction");
        addTrans.addActionListener(this);
        addTrans.setActionCommand("add");
        addTrans.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(addTrans);
        panel.setVisible(true);
        return panel;
    }

    /**
     * EFFECTS: Creates a panel with the balance of the account
     *
     * @return: JPanel with balance of the account
     */
    protected JPanel displayBalance() {
        JPanel panel = new JPanel(new GridLayout(1, 4));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black), "Information"));
        panel.add(new JLabel("Balance: " + balance));
        panel.setVisible(true);
        return panel;
    }

    /**
     * EFFECTS: Creates a panel with buttons to sort transactions
     *
     * @return: JPanel with 4 or 5 buttons to sort transactions
     */
    protected JPanel displayTransactionSorter() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black), "Sort Transaction"));
        defineButtons();
        panel.add(allTrans);
        panel.add(dayTrans);
        panel.add(typeTrans);
        panel.add(categoryTrans);
        panel.add(directionTrans);
        return panel;
    }

    /**
     * MODIFIES: This
     * EFFECTS: Adds all necessary action listeners and commands for buttons
     */
    protected void defineButtons() {
        allTrans = new JButton("All");
        allTrans.addActionListener(this);
        allTrans.setActionCommand("all");
        dayTrans = new JButton("By Day");
        dayTrans.addActionListener(this);
        dayTrans.setActionCommand("day");
        typeTrans = new JButton("By Type");
        typeTrans.addActionListener(this);
        typeTrans.setActionCommand("type");
        categoryTrans = new JButton("By Category");
        categoryTrans.addActionListener(this);
        categoryTrans.setActionCommand("category");
        directionTrans = new JButton("By Direction");
        directionTrans.addActionListener(this);
        directionTrans.setActionCommand("direction");
    }

    /**
     * MODIFIES: This, Account,
     * EFFECTS: If the action event is to save and exit, the account
     * is saved and the application is exited.
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        ChequingAccountController chq = (ChequingAccountController) controller;
        for (String s : viewStrings) {
            if (s.equals(e.getActionCommand())) {
                actionSwitchOne(s);
            }
        }
        if (e.getActionCommand().equals("add")) {
            new TransactionCreator(chq);
        } else if (e.getActionCommand().equals("save")) {
            Saver save = new Saver("./data/personalFinanceControllerSave.json");
            try {
                save.open();
                save.writeAccount(controller.getAcc());
                save.close();
                System.exit(0);
            } catch (FileNotFoundException fileNotFoundException) {
                new StartupErrorPopup("critical error", JFrame.EXIT_ON_CLOSE);
            }
        }
        if (e.getActionCommand().equals("exit")) {
            System.exit(0);
        }
    }

    /**
     * REQUIRES: a string that is an action command
     * MODIFIES: This, Account, controller
     * EFFECTS:If the action event is to add a transaction, a transaction is added and displayed
     * If the action event is to sort transactions by a certain date, category, direction or type, only the
     * transactions which satisify that criteria are displayed.
     * @param msg action command string
     */
    protected void actionSwitchOne(String msg) {
        ChequingAccountController chq = (ChequingAccountController) controller;
        switch (msg) {
            case "all":
                chq.viewDefault();
                dispose();
                break;
            case "day":
                new DateSelector(chq);
                break;
            case "type":
                new TypeSelector(chq);
                break;
            case "category":
                new CategorySelector(chq);
                break;
            case "direction":
                new DirectionSelector(chq);
        }
    }
}
