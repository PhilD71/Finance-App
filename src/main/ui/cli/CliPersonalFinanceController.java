package ui.cli;

import model.Account;
import model.ChequingAccount;
import model.SaveType;
import model.SavingAccount;
import persistence.Loader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static ui.cli.CliViewer.printString;
import static ui.cli.CliViewer.viewLoadingScreen;

/**
 * Controller for Personal Finance app
 */
public class CliPersonalFinanceController {
    public static boolean running;
    public static ChequingAccount chequingAccount = null;
    public static SavingAccount savingAccount = null;

    private CliSavingAccountControllerCli savControl = new CliSavingAccountControllerCli();
    private CliChequingCliAccountControllerCli chequeControl = new CliChequingCliAccountControllerCli();
    private boolean isSavingAccount;

    /**
     * MODIFIES: this, chequingAccount, savingAccount, savControl, chequeControl
     * EFFECTS: Creates the controller, runs startup, loads/creates accounts and then runs the controller
     */
    public CliPersonalFinanceController() {
        running = true;
        boolean startup = false;
        viewLoadingScreen();
        if (getScanner().nextInt() == 0) {
            while (!startup) {
                try {
                    createAccount();
                    startup = true;

                } catch (CliCreateAccountException e) {
                    printString("Invalid Input");
                    startup = false;
                }
            }
        } else {
            loadAccount();
        }
        runPersonalFinanceController();
    }

//    //MODIFIES: This
//    //EFFECTS: Sets up the user with an account of their choice, and inputs a balance.
//    private void accountSetup() {
//        System.out.println("To create a chequing account, input 1\nTo create a savings account, input 2");
//        int input = scanner.nextInt();
//        System.out.println("Please input your starting Balance:");
//        int balance = scanner.nextInt();
//
//        while (!((input == 1) | (input == 2))) {
//            System.out.println("the input selected: " + input + " is not valid. Select again.");
//            input = scanner.nextInt();
//        }
//
//        if (input == 1) {
//            chequingAccount = new ChequingAccount(balance);
//            isSavingAccount = false;
//        } else {
//            savingAccount = new SavingAccount(balance);
//            isSavingAccount = true;
//        }
//    }

    /**
     * MODIFIES: This
     * EFFECTS: Creates a chequing or saving account, throws exception if error is encountered while creating an account
     *
     * @throws CliCreateAccountException there is an error in account creation.
     */
    public void createAccount() throws CliCreateAccountException {
        CliViewer.viewOnStartup();
        int input = getScanner().nextInt();
        switch (input) {
            case 1:
                isSavingAccount = false;
                createChequing();
                break;
            case 2:
                isSavingAccount = true;
                createSaving();
                break;
            default:
                throw new CliCreateAccountException();
        }
    }

    /**
     * MODIFIES: chequingAccount
     * EFFECTS: creates a chequing account
     */
    private void createChequing() {
        chequeControl.createAccount();
    }

    /**
     * MODIFIES: savingAccount
     * EFFECTS: creates a saving Account
     */
    private void createSaving() {
        savControl.createAccount();
    }

    /**
     * REQUIRES: Account
     * MODIFIES: This
     * EFFECTS: Chooses what menu screen to show the user based on their chosen account.
     */
    private void runPersonalFinanceController() {
        while (running) {
            if (isSavingAccount) {
                savControl.executeMenu();
            } else {
                chequeControl.executeMenu();
            }
        }
    }

    /**
     * MODIFIES: savingAccount, chequingAccount, Loader
     * EFFECTS: Loads an account from the save file
     */
    private void loadAccount() {
        String location = "./data/personalFinanceControllerSave.json";
        boolean exists = false;
        try {
            Account acc;
            Loader loader = new Loader(location);
            acc = loader.read();
            if (acc.getSaveType() == SaveType.CHEQUING) {
                chequingAccount = (ChequingAccount) acc;
                isSavingAccount = false;
            } else {
                savingAccount = (SavingAccount) acc;
                isSavingAccount = true;
            }
        } catch (FileNotFoundException e) {
            printString("Account Doesn't Exist");
        } catch (IOException e) {
            printString("critical error");
            System.exit(0);
        }
    }


    /**
     * EFFECTS: creates a scanner
     *
     * @return scanner with input
     */
    public static Scanner getScanner() {
        return new Scanner(System.in);
    }
}
