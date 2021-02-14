package ui.cli;


import model.Transaction;
import model.Type;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Creates a Transaction
 */
public class CliTransactionCreator {
    private int input;
    private Scanner scanner = new Scanner(System.in);

    /**
     * EFFECTS: Creates a TransactionCreator
     */
    public CliTransactionCreator() {

    }

    /**
     * EFFECTS: returns a deposit transaction from given information collected by method
     */
    public Transaction createSavingTransaction() {
        System.out.println("Input amount:");
        int amount = scanner.nextInt();
        System.out.println("Input year:");
        int year = scanner.nextInt();
        System.out.println("Input month:");
        int month = scanner.nextInt();
        System.out.println("Input day:");
        int day = scanner.nextInt();
        LocalDate date = LocalDate.of(year, month, day);
        System.out.println("Select 1 if transaction is electronic\nSelect 2 if transaction is cash\n"
                + "Select 3 if transaction is cheque");
        int type = scanner.nextInt();
        System.out.println("is the transaction a (type):\npaycheck\netransfer\ngift");
        String category = scanner.next();

        return new Transaction(amount, date, intToType(type), category);
    }

    /**
     * EFFECTS: returns a withdrawal transaction from given information collected by method
     * @return transaction
     */
    public Transaction createChequingTransaction() {
        System.out.println("Input amount:");
        int amount = scanner.nextInt();
        System.out.println("Input year:");
        int year = scanner.nextInt();
        System.out.println("Input month:");
        int month = scanner.nextInt();
        System.out.println("Input day:");
        int day = scanner.nextInt();
        LocalDate date = LocalDate.of(year, month, day);
        System.out.println("Select 0 if transaction is on card\n"
                + "Select 1 if transaction is electronic\nSelect 2 if transaction is cash\n"
                + "Select 3 if transaction is cheque");
        int type = scanner.nextInt();
        System.out.println("is the transaction a (type):\nfood\npersonal\nhealth\nentertainment\nretail");
        String category = scanner.next();

        return new Transaction(amount, date, intToType(type), category);
    }

    /**
     * EFFECTS: turns an input int into a transaction type
     * @param input integer from scanner
     * @return transaction Type
     */
    private Type intToType(int input) {
        if (input == 0) {
            return Type.CARD;
        } else if (input == 1) {
            return Type.TRANSFER;
        } else if (input == 2) {
            return Type.CASH;
        } else {
            return Type.CHEQUE;
        }
    }
}

