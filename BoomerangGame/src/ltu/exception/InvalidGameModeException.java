package ltu.exception;

/**
 * Exception that should be thrown when the host player enters an invalid or
 * unsupported Game Mode
 */
public class InvalidGameModeException extends Exception {
    public InvalidGameModeException(String message) {
        super(message);
    }
}
