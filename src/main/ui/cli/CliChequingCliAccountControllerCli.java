package ui.cli;

import model.ChequingAccount;
import model.Type;
import model.SaveType;
import model.exceptions.TransactionException;
import persistence.Saver;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import static ui.cli.CliPersonalFinanceController.*;
import static ui.cli.CliViewer.printString;
import static ui.cli.CliViewer.printTransactionList;

/**
 * Controller for how the user interacts with the chequing account
 */
public class CliChequingCliAccountControllerCli implements CliAccountController, CliTransactionController {
    private CliChequingAccountCliViewer chequeView = new CliChequingAccountCliViewer();

    /**
     * MODIFIES: chequingAccount
     * EFFECTS: creates a new chequingAccount
     */
    public void createAccount() {
        chequeView.viewSetup();
        chequingAccount = new ChequingAccount(getScanner().nextInt(), SaveType.CHEQUING);
    }

    /**
     * REQUIRES: chequingAccount
     * MODIFIES: chequingAccount
     * EFFECTS: deposits, withdraws, adds transactions, checks balance and exits program
     */
    @Override
    public void executeMenu() {
        chequeView.viewMainMenu();
        CliTransactionCreator tc = new CliTransactionCreator();
        int input = (getScanner().nextInt());
        if (input == 1) {
            try {
                chequingAccount.transact(tc.createChequingTransaction());
                printString("Success!");
            } catch (TransactionException e) {
                printString("Insufficient Funds");
            }
        } else if (input == 2) {
            try {
                transactionMenu();
            } catch (InvalidInputError e) {
                printString("Invalid input");
            }
        } else if (input == 3) {
            System.out.println("Balance: " + chequingAccount.getBalance());
        } else if (input == 4) {
            exitError();
        }
    }

    private void exitError() {
        try {
            exit();
        } catch (FileNotFoundException e) {
            printString("error");
        }
    }

    @Override
    public void transactionMenu() throws InvalidInputError {
        chequeView.viewTransactionMenu();
        transactionOptions(getScanner().nextInt());
    }

    /**
     * MODIFIES: save file
     * EFFECTS: saves the data to a save file if the user wishes. Throws exception if can't save, exits program.
     *
     * @throws FileNotFoundException exception if file location is not found. this being thrown causes big errors.
     */
    public void exit() throws FileNotFoundException {
        String location = "./data/personalFinanceControllerSave.json";
        Saver save = new Saver(location);
        CliViewer.viewExitScreen();
        int input = getScanner().nextInt();
        if (input == 0) {
            save.open();
            save.writeAccount(chequingAccount);
            save.close();
        }
        CliViewer.goodbye();
        running = false;
    }

    public void transactionOptions(int input) throws InvalidInputError {
        switch (input) {
            case 1:
                printTransactionList(chequingAccount.getTransactions());
                break;
            case 2:
                chequeView.transactionType();
                transactionsFromType(getScanner().nextInt());
                break;
            case 3:
                transactionsFromDay();
                break;
            case 4:
                chequeView.viewTransactionDirection();
                transactionsFromDirection(getScanner().nextInt());
                break;
            case 5:
                chequeView.viewTransactionCategory();
                transactionsFromCategory(getScanner().next());
                break;
            default:
                throw new InvalidInputError("Not a possible choice");
        }
    }

    public void transactionsFromCategory(String str) {
        printTransactionList(chequingAccount.getTransactionsFromCategory(str));
    }

    public void transactionsFromDirection(int input) throws InvalidInputError {
        if (input == 1) {
            printTransactionList(chequingAccount.getTransactionsWithDirection(false));
        } else if (input == 2) {
            printTransactionList(chequingAccount.getTransactionsWithDirection(true));
        } else {
            throw new InvalidInputError("Not a possible choice");
        }
    }

    public void transactionsFromDay() throws InvalidInputError {
        System.out.println("Input year:");
        int year = getScanner().nextInt();
        System.out.println("Input month:");
        int month = getScanner().nextInt();
        System.out.println("Input day:");
        int day = getScanner().nextInt();
        try {
            LocalDate date = LocalDate.of(year, month, day);
            printTransactionList(chequingAccount.getTransactionsOnDay(date));
        } catch (Exception e) {
            throw new InvalidInputError("Invalid Date");
        }

    }

    public void transactionsFromType(int input) throws InvalidInputError {
        switch (input) {
            case 1:
                printTransactionList(chequingAccount.getTransactionsFromType(Type.CASH));
                break;
            case 2:
                printTransactionList(chequingAccount.getTransactionsFromType(Type.CARD));
                break;
            case 3:
                printTransactionList(chequingAccount.getTransactionsFromType(Type.CHEQUE));
                break;
            case 4:
                printTransactionList(chequingAccount.getTransactionsFromType(Type.TRANSFER));
                break;
            default:
                throw new InvalidInputError("Inavlid input");
        }
    }


}
