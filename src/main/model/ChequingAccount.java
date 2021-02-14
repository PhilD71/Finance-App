package model;

import model.exceptions.InsufficientFundsException;
import model.exceptions.TransactionException;

import java.util.ArrayList;
import java.util.List;

/**
 * An account that can have money taken out and put in
 */
public class ChequingAccount extends Account {

    /**
     * MODIFIES: This
     * EFFECTS: Constructs a ChequingAccount with balance, transactions and savetype.
     * @param balance balance of account
     * @param transactions list of transactions associated with account
     * @param type save type
     */
    public ChequingAccount(int balance, List<Transaction> transactions, SaveType type) {
        super(balance, transactions, type);
    }

    /**
     * MODIFIES: This
     * EFFECTS: Constructs a ChequingAccount with balance and savetype.
     * @param balance balance of account
     * @param type save type
     */
    public ChequingAccount(int balance, SaveType type) {
        super(balance, type);
    }

    @Override
    public void transact(Transaction transaction) throws TransactionException {
        if (transaction.isNegative()) {
            if (Math.abs(transaction.getValue()) > balance) {
                throw new InsufficientFundsException();
            }
        }
        balance += transaction.getValue();
        transactions.add(transaction);
        history.addTransaction(transaction);
        sortTransactions();
    }

    /**
     * EFFECTS: returns a list of all transactions with direction given by outgoing
     * @param outgoing direction of transaction
     * @return list of transactions
     */
    public List<Transaction> getTransactionsWithDirection(boolean outgoing) {
        List<Transaction> output = new ArrayList<Transaction>();
        for (Transaction t : transactions) {
            if (t.isNegative() == outgoing) {
                output.add(t);
            }
        }
        return output;
    }
}
