package model;

import model.exceptions.InsufficientFundsException;
import model.exceptions.TransactionException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Saveable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents an account with transactions tied to it
 */
public abstract class Account implements Saveable {
    public int balance;
    public List<Transaction> transactions;
    public SaveType type;
    protected History history;
    protected int initialBalance;

    public int getInitialBalance() {
        return initialBalance;
    }

    /**
     * MODIFIES: this
     * EFFECTS: constructs an account with balance and SaveType
     *
     * @param balance is the balance of the account
     * @param type    is the type of account
     */
    public Account(int balance, SaveType type) {
        this.balance = balance;
        this.type = type;
        initialBalance = balance;
        transactions = new ArrayList<Transaction>();
        history = new History(this);
    }

    /**
     * MODIFIES: This
     * EFFECTS: constructs an account with balance, list of transactions and SaveType
     *
     * @param balance      the balance of the account
     * @param transactions a list of transactions
     * @param type         the type of account
     */
    public Account(int balance, List<Transaction> transactions, SaveType type) {
        this.balance = balance;
        this.transactions = transactions;
        this.type = type;
        history = generateHistory();
    }

    /**
     * MODIFIES: history, this
     * EFFECTS:
     * @return history
     */
    protected History generateHistory() {
        History history = new History(this);
        sortTransactions();
        for (Transaction t : transactions) {
            history.addTransaction(t);
        }
        return history;
    }

    /**
     * @return balance
     * returns balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * @return list of transactions
     * returns transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }


    /**
     * REQUIRES: A transaction
     * MODIFIES: this
     * EFFECTS: adds transaction to transaction list, updates balance
     *
     * @param transaction is a transaction
     */
    public abstract void transact(Transaction transaction) throws TransactionException;

    /**
     * EFFECTS: returns transactions on a given day. returns empty list if none
     *
     * @param date date of transaction
     * @return a list of transactions
     */
    public List<Transaction> getTransactionsOnDay(LocalDate date) {
        List<Transaction> output = new ArrayList<Transaction>();
        for (Transaction t : transactions) {
            if (t.getDate().equals(date)) {
                output.add(t);
            }
        }
        return output;
    }

    /**
     * EFFECTS: returns a list of transactions from a certain category. returns empty list if none.
     *
     * @param category category of transaction
     * @return list of transactions
     */
    public List<Transaction> getTransactionsFromCategory(String category) {
        List<Transaction> output = new ArrayList<Transaction>();
        for (Transaction t : transactions) {
            if (t.getCategory().equals(category)) {
                output.add(t);
            }
        }
        return output;
    }

    /**
     * EFFECTS: returns a list of transactions of a certain type. returns empty list if none.
     *
     * @param type type of transaction
     * @return list of transactions
     */
    public List<Transaction> getTransactionsFromType(Type type) {
        List<Transaction> output = new ArrayList<Transaction>();
        for (Transaction t : transactions) {
            if (t.getType().equals(type)) {
                output.add(t);
            }
        }
        return output;
    }


    /**
     * EFFECTS: Sorts the transactions in order of smallest date -> largest date
     */
    public void sortTransactions() {
        if (transactions.size() > 2) {
            for (int i = 0; i < transactions.size(); i++) {
                for (int j = 0; j < transactions.size() - i - 1; j++) {
                    LocalDate dateOne = transactions.get(j).getDate();
                    LocalDate dateTwo = transactions.get(j + 1).getDate();
                    Transaction transactionOne = transactions.get(j);
                    Transaction transactionTwo = transactions.get(j + 1);
                    if (dateOne.compareTo(dateTwo) > 0) {
                        Transaction temp = transactionTwo;
                        transactions.set(j + 1, transactionOne);
                        transactions.set(j, temp);
                    }
                }
            }
        }
    }

    public History getHistory() {
        return history;
    }

    /**
     * MODIFIES: json object
     * EFFECTS: saves account as a json object with balance, account type and a jsonArray of transactions
     *
     * @return a json object
     */
    @Override
    public JSONObject saveToJson() {
        JSONObject accountJson = new JSONObject();
        accountJson.put("balance", balance);
        accountJson.put("account type", type);
        accountJson.put("transactions", transactionToJson());
        return accountJson;
    }

    /**
     * MODIFIES: json array
     * EFFECTS: saves each transaction into a json Array
     *
     * @return a json array
     */
    protected JSONArray transactionToJson() {
        JSONArray jsonList = new JSONArray();
        for (Transaction t : transactions) {
            jsonList.put(t.saveToJson());
        }
        return jsonList;
    }

    /**
     * @return Savetype
     */
    public SaveType getSaveType() {
        return type;
    }
}
