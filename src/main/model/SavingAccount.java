package model;

import model.exceptions.IllegalWithdrawalException;
import model.exceptions.TransactionException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Creates a deposit only account that can give predictions and keeps track of a saving goal
 */
public class SavingAccount extends Account {



    /**
     * MODIFIES: This
     * EFFECTS: constructs a saving account with balance, transactions, saving goal and type.
     *
     * @param balance      balance of account
     * @param transactions transaction list
     * @param type         save type
     */
    public SavingAccount(int balance, List<Transaction> transactions, SaveType type) {
        super(balance, transactions, type);
    }


    /**
     * MODIFIES: This
     * EFFECTS: constructs a saving account with balance and type.
     *
     * @param balance balance of account
     * @param type    save type
     */
    public SavingAccount(int balance, SaveType type) {
        super(balance, type);
    }

    /**
     * REQUIRES: At least one transaction of type incoming in the list of transactions
     * EFFECTS: Returns a predicted savings amount based on your previous savings.
     *
     * @param year year
     */
    public int savingsPrediction(int year) {
        double savingPerYear = (getSavedAmount() / getTimePeriod()) * 365;
        return ((int) savingPerYear * year) + balance;
    }

    public void setGoal(int amount) {
        history.setGoal(amount);
    }

    public boolean checkGoal() {
        return history.checkGoal();
    }

    public int getGoal() {
        return history.getGoal();
    }

    @Override
    public void transact(Transaction transaction) throws TransactionException {
        if (transaction.isNegative()) {
            throw new IllegalWithdrawalException();
        }
        balance += transaction.getValue();
        transactions.add(transaction);
        history.addTransaction(transaction);
        sortTransactions();
    }

    /**
     * REQUIRES: transactions with only transactions of type incoming
     * EFFECTS: sums up all money saved in every transaction recorded.
     *
     * @return sum of total saved amount
     */
    private int getSavedAmount() {
        int sum = 0;
        for (Transaction t : transactions) {
            sum += t.getValue();
        }
        return sum;
    }

    /**
     * REQUIRES: transactions has 1 or more dates
     * EFFECT: gets the amount of days between the earliest date and the most recent one.
     *
     * @return time period in days between the first and last transaction
     */
    //TODO find min date
    private int getTimePeriod() {
        if (transactions.size() == 1) {
            return 1;
        }
        LocalDate dateMin = transactions.get(0).getDate();
        LocalDate dateMax = transactions.get(0).getDate();
        for (Transaction t : transactions) {
            if (t.getDate().compareTo(dateMax) > 0) {
                dateMax = t.getDate();
            }
        }
        long daysBetween = ChronoUnit.DAYS.between(dateMin, dateMax);
        return (int) daysBetween + 1;

    }

}
