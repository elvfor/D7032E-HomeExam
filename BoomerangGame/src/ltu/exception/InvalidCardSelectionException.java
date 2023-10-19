package ltu.exception;

/**
 * Exception that should be thrown when player chooses an invalid card
 */
public class InvalidCardSelectionException extends Exception {
    public InvalidCardSelectionException(String message) {
        super(message);
    }
}
