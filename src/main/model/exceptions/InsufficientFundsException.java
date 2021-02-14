package model.exceptions;

public class InsufficientFundsException extends TransactionException {
    public InsufficientFundsException() {
        super("Insufficient Funds");
    }
}
