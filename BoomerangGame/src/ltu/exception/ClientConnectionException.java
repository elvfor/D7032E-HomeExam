package ltu.exception;

import java.io.IOException;

/**
 * Exception that should be thrown when the client can't connect to server
 */
public class ClientConnectionException extends Exception {
    public ClientConnectionException(String message, IOException e) {
        super(message);
    }

}
