package ltu.exception;

import java.io.IOException;

public class ClientConnectionException extends Exception {
    public ClientConnectionException(String message, IOException e) {
        super(message);
    }

}
