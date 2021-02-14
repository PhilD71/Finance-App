package model.exceptions;

public class IllegalWithdrawalException extends TransactionException {
    public IllegalWithdrawalException() {
        super("Cannot withdraw from this account!");
    }
}
