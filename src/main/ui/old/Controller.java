package ui.old;

import model.Account;
import model.Transaction;
import model.Type;
import model.exceptions.IllegalWithdrawalException;
import model.exceptions.TransactionException;
import ui.exception.UiException;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * an abstract controller for an account
 */
public abstract class Controller {
    protected Account acc;
    protected LocalDate date;
    protected String category;
    protected Type type;

    /**
     * MODIFIES: This
     * EFFECTS: constructs an instance of controller with account
     *
     * @param acc account
     */
    public Controller(Account acc) {
        this.acc = acc;
    }

    public Account getAcc() {
        return acc;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public Type getType() {
        return type;
    }

    public int getBalance() {
        return acc.getBalance();
    }

    /**
     * MODIFIES: This
     * EFFECTS: sets account
     *
     * @param acc Account
     */
    public void setAcc(Account acc) {
        this.acc = acc;
    }

    /**
     * MODIFIES: This
     * EFFECTS: sets date
     *
     * @param date LocalDate date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * MODIFIES: This
     * EFFECTS: sets category
     *
     * @param category String
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * MODIFIES: This
     * EFFECTS: Sets type
     *
     * @param type Type
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * MODIFIES: Account
     * EFFECTS: If account can process the transaction, it processes. Otherwise, throws exception
     *
     * @param t transaction
     * @throws TransactionException thrown when account cannot transact
     */
    public void transact(Transaction t) throws TransactionException {
        acc.transact(t);
    }

    /**
     * EFFECTS: gets all transactions from account
     *
     * @return List of Transaction
     */
    public List<Transaction> getTransactions() {
        return acc.getTransactions();
    }

    /**
     * EFFECTS: gets transactions from account that match date field
     *
     * @return List of Transaction
     */
    public List<Transaction> getTransactionsDay() {
        return acc.getTransactionsOnDay(date);
    }

    /**
     * EFFECTS: gets transactions from account that match type field
     *
     * @return List of Transaction
     */
    public List<Transaction> getTransactionsType() {
        return acc.getTransactionsFromType(type);
    }

    /**
     * EFFECTS: gets transactions from account that match category field
     *
     * @return List of Transaction
     */
    public List<Transaction> getTransactionsCategory() {
        return acc.getTransactionsFromCategory(category);
    }

    /**
     * EFFECTS: Closes all UI windows in application
     */
    public static void closeWindows() {
        java.awt.Window[] win;
        win = Window.getWindows();
        for (int i = 0; i < win.length; i++) {
            win[i].dispose();
        }
    }

    /**
     * EFFECTS: Creates a transaction with information from viewer, throws exception if error with transaction
     * is wrong
     *
     * @param dateString date in string format
     * @param money      value of transaction
     * @param type       type of transaction
     * @param cat        category of transaction
     * @throws TransactionException thrown if error with transaction is encountered
     */
    public void processTransaction(String dateString, String money, Type type, String cat) throws TransactionException {
        String yr = dateString.substring(0, dateString.indexOf('-'));
        dateString = dateString.substring(dateString.indexOf('-') + 1);
        String month = dateString.substring(0, dateString.indexOf('-'));
        String day = dateString.substring(dateString.indexOf('-') + 1);
        LocalDate date = (LocalDate.of(Integer.parseInt(yr), Integer.parseInt(month), Integer.parseInt(day)));
        transact(new Transaction(Integer.parseInt(money), date,
                type, cat));
        closeWindows();
        viewDefault();
    }

    /**
     * EFFECT: Displays all transactions
     */
    public abstract void viewDefault();

    /**
     * EFFECT: Displays transactions with selected date
     */
    protected abstract void viewDate();

    /**
     * EFFECT: Displays transactions with selected type
     */
    protected abstract void viewType();

    /**
     * EFFECT: Displays transactions with selected category
     */
    protected abstract void viewCategory();

    public void sortTransactions() {
        acc.sortTransactions();
    }
}
