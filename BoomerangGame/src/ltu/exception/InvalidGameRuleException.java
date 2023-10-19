package ltu.exception;

/**
 * Exception that should be thrown when the host player enters an invalid or
 * unsupported Game Rule
 */
public class InvalidGameRuleException extends Exception {
    public InvalidGameRuleException(String message) {
        super(message);
    }
}
