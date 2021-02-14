package ui.cli;

/**
 * Viewer for Chequing Account Controller
 */
public class CliChequingAccountCliViewer extends CliViewer {
    public CliChequingAccountCliViewer() {

    }

    /**
     * EFFECTS: Displays main menu
     */
    @Override
    public void viewMainMenu() {
        viewMenuBar();
        printString("To transact, select 1\n"
                + "To view transaction history, select 2\n"
                + "To see your balance, press 3\nTo exit press 4");
    }

    @Override
    public void viewTransactionMenu() {
        printString("To view all transactions, press 1");
        printString("To view transactions of a certain type, press 2");
        printString("To view transactions on a certain day, press 3");
        printString("To view withdrawals or deposits, press 4");
        printString("To vew transactions of a certain category, press 5");
    }

    public void viewTransactionDirection() {
        printString("For deposit history, select 1");
        printString("For withdrawal history, select 2");
    }

}
