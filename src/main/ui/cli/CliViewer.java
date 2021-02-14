package ui.cli;

import model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Viewer for various menu options
 */
public abstract class CliViewer {

    /**
     * Views main menu
     */
    public abstract void viewMainMenu();

    /**
     * Initialization loading screen
     */
    public static void viewLoadingScreen() {
        printString("Would you like to create or load an account?");
        printString("To create, press 0");
        printString("To load, press 1");

    }

    /**
     * Startup View
     */
    public static void viewOnStartup() {
        printString("To create a chequing account, input 1\nTo create a savings account, input 2");
    }

    /**
     * Account setup view
     */
    public static void viewSetup() {
        printString("Please input your balance:");
    }

    /**
     * Shortened sout statement for simplicity
     *
     * @param str string
     */
    public static void printString(String str) {
        System.out.println(str);
    }

    /**
     * Shortened sout statment for simplicity
     *
     * @param i integer
     */
    public static void printInt(int i) {
        System.out.println(i);
    }

    /**
     * Nifty menu bar!
     */
    public static void viewMenuBar() {
        printString("\n\n-------- M E N U --------\n");
    }


    /**
     * Exit screen
     */
    public static void viewExitScreen() {
        printString("Would you like to save?");
        printString("Press 0 to save and exit");
        printString("Press 1 to exit");
    }

    /**
     * goodbye!
     */
    public static void goodbye() {
        printString("Goodbye!");
    }

    /**
     * EFFECTS: Prints the list of transactions
     *
     * @param transactions list of transactions
     */
    public static void printTransactionList(List<Transaction> transactions) {
        List<String> output = new ArrayList<String>();
        printString("Transaction List:");
        for (Transaction t : transactions) {
            transactionToString(t);
        }
    }

    /**
     * EFFECTS: takes a transaction and puts all of its information into a string output.
     */
    private static void transactionToString(Transaction t) {
        String type;
        String outgoing;
        if (t.isNegative()) {
            outgoing = "outgoing";
        } else {
            outgoing = "incoming";
        }
        printString("Amount: " + Integer.toString(t.getValue()) + " | Date: " + t.getDate().toString()
                + " | Type: " + t.getType() + " | Direction: " + outgoing + " | Category: " + t.getCategory());
    }

    /**
     * EFFECTS: Displays a string for the transaction by day sorter
     */
    public void viewTransactionCategory() {
        printString("Please enter the category you would like to sort by: ");
    }

    public abstract void viewTransactionMenu();

    /**
     * EFFECTS: Displays a string for the transaction by type sorter
     */
    public void transactionType() {
        printString("Please select the type you would like to get your transaction from: ");
        printString("For cash, select 1");
        printString("For card, select 2");
        printString("For cheque, select 3");
        printString("For transfer, select 4");
    }
}
