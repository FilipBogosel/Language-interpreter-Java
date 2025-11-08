package model.exception;

public class ReadError extends RuntimeException {
    public ReadError(String message) {
        super(message);
    }
}
