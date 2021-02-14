package ui.cli;

public interface CliTransactionController {
    /**
     * REQUIRES: an int that corresponds to a case in the switch statement/if-else statement
     * EFFECTS: Selects an option to sort the transactions by when viewed
     */
    void transactionMenu() throws InvalidInputError;

    void transactionOptions(int input) throws InvalidInputError;

    void transactionsFromCategory(String str);

    void transactionsFromType(int input) throws InvalidInputError;

    void transactionsFromDay() throws InvalidInputError;
}
