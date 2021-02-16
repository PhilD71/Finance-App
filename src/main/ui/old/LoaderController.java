package ui.old;

import model.Account;
import model.ChequingAccount;
import model.SaveType;
import model.SavingAccount;
import persistence.Loader;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Handles loading of the data
 */
public class LoaderController {

    /**
     * MODIFIES: This
     * EFFECTS: Creates a chequing or saving account controller and view if data is read succesfully
     * if data is unsuccessfully read, error thrown, application closed.
     */
    public LoaderController() {
        String location = "./data/personalFinanceControllerSave.json";
        try {
            Account acc;
            Loader loader = new Loader(location);
            acc = loader.read();
            acc.sortTransactions();
            if (acc.getSaveType() == SaveType.CHEQUING) {
                new ChequingAccountController((ChequingAccount) acc);
            } else {
                new SavingAccountController((SavingAccount) acc);
            }
        } catch (FileNotFoundException e) {
            new StartupErrorPopup("Account Doesn't Exist", JFrame.DISPOSE_ON_CLOSE);
        } catch (IOException e) {
            new StartupErrorPopup("Critical error", JFrame.EXIT_ON_CLOSE);
        }
    }
}
