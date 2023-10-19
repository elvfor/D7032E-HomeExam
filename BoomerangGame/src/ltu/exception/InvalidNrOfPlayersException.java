package ltu.exception;

/**
 * Exception that should be thrown when the host player enters an invalid nr of
 * players to run the game with
 */
public class InvalidNrOfPlayersException extends Exception {

    public InvalidNrOfPlayersException(String message) {
        super(message);
    }

}
