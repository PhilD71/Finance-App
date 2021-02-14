package ui.cli;

/**
 * Viewer for SavingAccountController
 */
public class CliSavingAccountCliViewer extends CliViewer {

    /**
     * EFFECTS: Displays menu
     */
    @Override
    public void viewMainMenu() {
        viewMenuBar();
        printString("To track a deposit, select 1\nTo view transaction history, select 2\n"
                        + "To set a saving goal, select 3\n"
                + "To get your predicted savings, press 4\n"
                + "To see your balance, press 5\nTo exit press 6");;
    }

    @Override
    public void viewTransactionMenu() {
        printString("To view all transactions, press 1");
        printString("To view transactions of a certain type, press 2");
        printString("To view transactions on a certain day, press 3");
        printString("To vew transactions of a certain category, press 4");
    }




}
