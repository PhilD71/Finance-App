package ui.cli;

import java.io.FileNotFoundException;

/**
 * Is a controller for how the user interacts with an accont
 */
public interface CliAccountController {

    /**
     * creates an account
     */
    public void createAccount();

    /**
     * exectues the main menu
     */
    public void executeMenu();

    /**
     * exits
     * @throws FileNotFoundException throws if cannot write save
     */
    public void exit() throws FileNotFoundException;
}
