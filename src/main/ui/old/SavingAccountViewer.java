package ui.old;

import model.SavingAccount;
import model.Transaction;
import persistence.Saver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * A viewer for a saving account
 */
public class SavingAccountViewer extends AccountViewer {
    private JButton savHistory;
    private JButton goalSet;

    /**
     * MODIFIES: This
     * EFFECTS: Constructs a SavingAccountViewer
     *
     * @param controller controller for viewer
     */
    public SavingAccountViewer(Controller controller) {
        super(controller);
        setTitle("Saving Account Tracker");
    }

    /**
     * MODIFIES: This
     * EFFECTS: Constructs a SavingAccountViewer
     *
     * @param controller      controller for viewer
     * @param transactionList list of transactions
     */
    public SavingAccountViewer(Controller controller, List<Transaction> transactionList) {
        super(controller, transactionList);
        setTitle("Saving Account Tracker");
    }

    /**
     * EFFECTS: creates panel with button to add transactions
     *
     * @return JPanel with transaction adder button and a display history button
     */
    @Override
    protected JPanel displayTransactionAdder() {
        JPanel panel = new JPanel();
        savHistory = new JButton("Saving History");
        savHistory.addActionListener(this);
        goalSet = new JButton("Set Goal");
        goalSet.setActionCommand("set");
        goalSet.addActionListener(this);
        savHistory.setActionCommand("hist");
        addTrans = new JButton("Add Transaction");
        addTrans.addActionListener(this);
        addTrans.setActionCommand("add");
        addTrans.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(addTrans);
        panel.add(savHistory);
        panel.add(goalSet);
        panel.setVisible(true);
        return panel;
    }

    /**
     * EFFECTS: Creates a panel with the balance of the account
     *
     * @return: JPanel with balance of the account
     */
    protected JPanel displayBalance() {
        SavingAccountController savCon = (SavingAccountController) controller;
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JPanel innerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel innerRight = new JPanel((new FlowLayout(FlowLayout.RIGHT)));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black), "Information"));
        innerLeft.add(new JLabel("Balance: " + balance));
        innerRight.add(new JLabel("Goal of " + savCon.getGoal() + " " + metGoal(savCon.checkGoal())));
        panel.add(innerLeft);
        panel.add(innerRight);
        panel.setVisible(true);
        return panel;
    }

    private String metGoal(boolean met) {
        if (met) {
            return "is met!";
        } else {
            return "is not met";
        }
    }

    /**
     * EFFECTS: Creates a panel with buttons to sort transactions
     *
     * @return: JPanel with 4 buttons to sort transactions
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
    }

    /**
     * MODIFIES: This, Account,
     * EFFECTS: If the action event is to save and exit, the account
     * is saved and the application is exited.
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        SavingAccountController sav = (SavingAccountController) controller;
        for (String s : viewStrings) {
            if (s.equals(e.getActionCommand())) {
                actionSwitchOne(s);
            }
        }
        if (e.getActionCommand().equals("add")) {
            new TransactionCreator(sav);
        } else if (e.getActionCommand().equals("save")) {
            save();
        } else if (e.getActionCommand().equals("hist")) {
            sav.displayHistory();
        } else if (e.getActionCommand().equals("set")) {
            sav.setGoalPopup();
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
     *
     * @param msg action command string
     */
    protected void actionSwitchOne(String msg) {
        SavingAccountController sav = (SavingAccountController) controller;
        switch (msg) {
            case "all":
                sav.viewDefault();
                dispose();
                break;
            case "day":
                new DateSelector(sav);
                break;
            case "type":
                new TypeSelector(sav);
                break;
            case "category":
                new CategorySelector(sav);
                break;
        }
    }

    /**
     * EFFECTS: Saves account details to a JSON
     */
    private void save() {
        Saver save = new Saver("./data/personalFinanceControllerSave.json");
        try {
            save.open();
            save.writeAccount((SavingAccount) controller.getAcc());
            save.close();
            System.exit(0);
        } catch (FileNotFoundException fileNotFoundException) {
            new StartupErrorPopup("critical error", JFrame.EXIT_ON_CLOSE);
        }
    }

}
