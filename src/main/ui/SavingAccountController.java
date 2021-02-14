package ui;

import model.SavingAccount;

/**
 * Controlls the saving account, modifying it as necessary and calling views to display information
 */
public class SavingAccountController extends Controller {
    private SavingAccount savingAccount;

    /**
     * MODIFIES: This
     * EFFECTS: Constructs an instance of this controller
     *
     * @param savingAccount a saving account
     */
    public SavingAccountController(SavingAccount savingAccount) {
        super(savingAccount);
        this.savingAccount = savingAccount;
        viewDefault();
    }

    /**
     * EFFECTS: Displays all transactions in home screen
     */
    @Override
    public void viewDefault() {
        SavingAccount savAcc = (SavingAccount) acc;
        savAcc.sortTransactions();
        new SavingAccountViewer(this);
    }

    /**
     * EFFECTS: Only displays transactions with date that matches value in date field in home screen
     */
    protected void viewDate() {
        acc.sortTransactions();
        new SavingAccountViewer(this, this.getTransactionsDay());
    }

    /**
     * EFFECTS Only displays transactions with type that matches value in type field in home screen
     */
    protected void viewType() {
        acc.sortTransactions();
        new SavingAccountViewer(this, this.getTransactionsType());
    }

    /**
     * EFFECTS Only displays transactions with category that matches value in category field in home screen
     */
    protected void viewCategory() {
        acc.sortTransactions();
        new SavingAccountViewer(this, this.getTransactionsCategory());
    }


    /**
     * EFFECTS: Displays histogram of saved transactions
     */
    public void displayHistory() {
        acc.sortTransactions();
        new HistoryViewer(getAcc());
    }

    public void setGoal(int amount) {
        savingAccount.setGoal(amount);
    }

    public boolean checkGoal() {
        return savingAccount.checkGoal();
    }

    public int getGoal() {
        return savingAccount.getGoal();
    }

    public void setGoalPopup() {
        new GoalSetter(this);
    }
}
