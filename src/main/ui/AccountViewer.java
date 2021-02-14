package ui;

import model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * A viewer for an abstract account
 */
public abstract class AccountViewer extends JFrame implements ActionListener {
    protected static final int WIDTH_TRANSACTION = 700;
    protected static final int HEIGHT_TRANSACTION = 500;
    protected static final int WIDTH = 700;
    protected static final int HEIGHT = 830;

    protected int balance;
    protected List<Transaction> transactionsList;
    protected Controller controller;
    protected JPanel contentPane;
    protected JButton addTrans;
    protected JButton allTrans;
    protected JButton dayTrans;
    protected JButton typeTrans;
    protected JButton categoryTrans;
    protected JButton directionTrans;
    protected JButton exitButton;
    protected JButton saveButton;
    protected String[] viewStrings = {"all", "day", "type", "category", "direction"};

    /**
     * MODIFIES: This
     * EFFECTS: Constructs an AccountViewer
     *
     * @param controller controller for viewer
     */
    public AccountViewer(Controller controller) {
        super();
        this.controller = controller;
        balance = controller.getBalance();
        transactionsList = controller.getTransactions();
        setupContentPanel();
    }

    /**
     * MODIFIES: This
     * EFFECTS: Constructs an AccountViewer
     *
     * @param controller      controller for viewer
     * @param transactionList list of transactions
     */
    public AccountViewer(Controller controller, List<Transaction> transactionList) {
        super();
        this.controller = controller;
        this.balance = controller.getBalance();
        this.transactionsList = transactionList;
        setupContentPanel();
    }

    /**
     * MODIFIES: This
     * EFFECTS: Sets up a frame, adds panels
     */
    protected void setupContentPanel() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(makeContentPane());
        setSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
        setResizable(false);
        pack();
    }

    /**
     * EFFECTS: makes the main content panel
     *
     * @return JPanel with content
     */
    protected JPanel makeContentPane() {
        contentPane = new JPanel();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(displayBalance());
        panel.add(displayTransactionAdder());
        panel.add(displayTransactions());
        panel.add(displayTransactionSorter());
        panel.add(exit());
        panel.setSize(WIDTH, HEIGHT);
        contentPane.add(panel);
        contentPane.setSize(WIDTH, HEIGHT);
        contentPane.setVisible(true);
        return contentPane;
    }

    /**
     * EFFECTS: creates panel with button to add transactions
     *
     * @return JPanel with transaction adder button
     */
    protected abstract JPanel displayTransactionAdder();

    /**
     * EFFECTS: returns a scroll panel that displays all transactions tied to account
     *
     * @return Scroll Pane with transactions added
     */
    protected JScrollPane displayTransactions() {
        controller.sortTransactions();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black), "transactions"));
        panel.setBackground(Color.white);
        String[] transactions = new String[transactionsList.size()];
        int count = 0;
        for (Transaction t : transactionsList) {
            transactions[count] = t.transactionToString();
            count++;
        }
        panel.add(new JList(transactions));
        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setViewportView(panel);
        scrollPanel.setSize(new Dimension(WIDTH_TRANSACTION, HEIGHT_TRANSACTION));
        scrollPanel.setVisible(true);
        return scrollPanel;
    }

    /**
     * EFFECTS: Returns a panel with two exit buttons, save and exit, or just exit
     *
     * @return: JPanel with two buttons
     */
    protected JPanel exit() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save and Exit");
        exitButton = new JButton("Exit");
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(this);
        saveButton.setActionCommand("save");
        saveButton.addActionListener(this);
        panel.add(exitButton);
        panel.add(saveButton);
        return panel;
    }

    /**
     * EFFECTS: Creates a panel with the balance of the account
     *
     * @return: JPanel with balance of the account
     */
    protected abstract JPanel displayBalance();

    /**
     * EFFECTS: Creates a panel with buttons to sort transactions
     *
     * @return: JPanel with 4 or 5 buttons to sort transactions
     */
    protected abstract JPanel displayTransactionSorter();

    /**
     * MODIFIES: This
     * EFFECTS: Adds all necessary action listeners and commands for buttons
     */
    protected abstract void defineButtons();

    /**
     * MODIFIES: This, Account,
     * EFFECTS: If the action event is to save and exit, the account
     * is saved and the application is exited.
     * @param e action event
     */
    public abstract void actionPerformed(ActionEvent e);

    /**
     * REQUIRES: a string that is an action command
     * MODIFIES: This, Account, controller
     * EFFECTS:If the action event is to add a transaction, a transaction is added and displayed
     * If the action event is to sort transactions by a certain date, category, direction or type, only the
     * transactions which satisify that criteria are displayed.
     * @param msg action command string
     */
    protected abstract void actionSwitchOne(String msg);
}