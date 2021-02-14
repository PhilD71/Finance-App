package ui.cli;

/**
 * error for invalid input
 */
public class InvalidInputError extends Exception {
    public InvalidInputError(String str) {
        super(str);
    }
}
