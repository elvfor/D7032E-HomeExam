package ltu.exception;

/**
 * Exception that should be thrown when the player chooses an invalid activity
 */
public class InvalidChoiceOfActivityException extends Exception {
    public InvalidChoiceOfActivityException(String message) {
        super(message);
    }
}
