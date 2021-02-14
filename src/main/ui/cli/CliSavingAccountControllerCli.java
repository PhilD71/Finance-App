package ui.cli;

import model.SaveType;
import model.SavingAccount;
import model.Type;
import model.exceptions.TransactionException;
import persistence.Saver;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import static ui.cli.CliPersonalFinanceController.*;
import static ui.cli.CliViewer.printString;
import static ui.cli.CliViewer.printInt;
import static ui.cli.CliViewer.printTransactionList;

/**
 * Controller for how user interacts with the saving account
 */
public class CliSavingAccountControllerCli implements CliAccountController, CliTransactionController {
    private CliSavingAccountCliViewer savView = new CliSavingAccountCliViewer();

    /**
     * MODIFIES: savingAccount
     * EFFECTS: creates a new saving account
     */
    @Override
    public void createAccount() {
        savView.viewSetup();
        savingAccount = new SavingAccount(getScanner().nextInt(), SaveType.SAVING);
    }

    /**
     * EFFECTS: runs the selection process
     */
    @Override
    public void executeMenu() {
        savView.viewMainMenu();
        selectMenuItem(getScanner().nextInt());
    }

    /**
     * REQUIRES: savingAccount
     * MODIFIES: savingAccount
     * EFFECTS: deposits, adds transactions, checks balance and exits program
     */
    private void selectMenuItem(int input) {
        switch (input) {
            case 1:
                tryDeposit();
                break;
            case 2:
                tryWithdraw();
                break;
//            Commented out for future functionality
//            case 3:
//                SavingGoalCreator sgc = new SavingGoalCreator();
//                savingAccount.setSavingGoal(sgc.createSavingGoal());
//                System.out.println("saving goal created!");
//                break;
            case 4:
                printString("How many years in the future would you like your prediction?");
                printInt(savingAccount.savingsPrediction(getScanner().nextInt()));
                break;
            case 5:
                printString("Balance:" + savingAccount.getBalance());
                break;
            case 6:
                try {
                    exit();
                } catch (FileNotFoundException e) {
                    System.out.println("error");
                }
                running = false;
        }

    }

    private void tryWithdraw() {
        try {
            transactionMenu();
        } catch (InvalidInputError e) {
            printString("Invalid input");
        }
    }

    private void tryDeposit() {
        CliTransactionCreator tc = new CliTransactionCreator();
        try {
            savingAccount.transact(tc.createSavingTransaction());
        } catch (TransactionException e) {
            printString("Negatron!");
        }
    }


    @Override
    public void transactionMenu() throws InvalidInputError {
        savView.viewTransactionMenu();
        transactionOptions(getScanner().nextInt());
    }

    @Override
    public void transactionOptions(int input) throws InvalidInputError {
        switch (input) {
            case 1:
                printTransactionList(savingAccount.getTransactions());
                break;
            case 2:
                savView.transactionType();
                transactionsFromType(getScanner().nextInt());
                break;
            case 3:
                transactionsFromDay();
                break;
            case 4:
                savView.viewTransactionCategory();
                transactionsFromCategory(getScanner().next());
                break;
            default:
                throw new InvalidInputError("Not a possible choice");
        }
    }

    @Override
    public void transactionsFromCategory(String str) {
        printTransactionList(savingAccount.getTransactionsFromCategory(str));
    }

    @Override
    public void transactionsFromType(int input) throws InvalidInputError {
        switch (input) {
            case 1:
                printTransactionList(savingAccount.getTransactionsFromType(Type.CASH));
                break;
            case 2:
                printTransactionList(savingAccount.getTransactionsFromType(Type.CARD));
                break;
            case 3:
                printTransactionList(savingAccount.getTransactionsFromType(Type.CHEQUE));
                break;
            case 4:
                printTransactionList(savingAccount.getTransactionsFromType(Type.TRANSFER));
                break;
            default:
                throw new InvalidInputError("Inavlid input");
        }
    }

    @Override
    public void transactionsFromDay() throws InvalidInputError {
        System.out.println("Input year:");
        int year = getScanner().nextInt();
        System.out.println("Input month:");
        int month = getScanner().nextInt();
        System.out.println("Input day:");
        int day = getScanner().nextInt();
        try {
            LocalDate date = LocalDate.of(year, month, day);
            printTransactionList(savingAccount.getTransactionsOnDay(date));
        } catch (Exception e) {
            throw new InvalidInputError("Invalid Date");
        }
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
            save.writeAccount(savingAccount);
            save.close();
        }
        CliViewer.goodbye();
    }
}
