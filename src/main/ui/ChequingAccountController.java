package ui;


import model.Account;
import model.ChequingAccount;

import java.time.LocalDate;

/**
 * Controlls the chequing account, modifying it as necessary and calling views to display information
 */
public class ChequingAccountController extends Controller {
    protected boolean outgoing;

    /**
     * MODIFIES: This
     * EFFECTS: Constructs an instance of this controller
     * @param chequingAccount a chequing account
     */
    public ChequingAccountController(ChequingAccount chequingAccount) {
        super(chequingAccount);
        viewDefault();
    }

    public boolean isOutgoing() {
        return outgoing;
    }

    /**
     * MODIFIES: This
     * EFFECTS: sets direction of outgoing parameter
     * @param outgoing boolean
     */
    public void setDirection(boolean outgoing) {
        this.outgoing = outgoing;
    }

    /**
     * EFFECTS: Only displays transactions with the direction of value in field direction in home screen
     */
    public void viewDirection() {
        ChequingAccount chqAcc = (ChequingAccount) acc;
        chqAcc.sortTransactions();
        new ChequingAccountViewer(this, chqAcc.getTransactionsWithDirection(outgoing));
    }

    /**
     * EFFECTS: Displays all transactions in home screen
     */
    public void viewDefault() {
        acc.sortTransactions();
        new ChequingAccountViewer(this);
    }

    /**
     * EFFECTS: Only displays transactions with date that matches value in date field in home screen
     */
    protected void viewDate() {
        acc.sortTransactions();
        new ChequingAccountViewer(this, this.getTransactionsDay());
    }

    /**
     * EFFECTS Only displays transactions with type that matches value in type field in home screen
     */
    protected void viewType() {
        acc.sortTransactions();
        new ChequingAccountViewer(this, this.getTransactionsType());
    }

    /**
     * EFFECTS Only displays transactions with category that matches value in category field in home screen
     */
    protected void viewCategory() {
        acc.sortTransactions();
        new ChequingAccountViewer(this, this.getTransactionsCategory());
    }
}
