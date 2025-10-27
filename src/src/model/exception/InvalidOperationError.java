package model.exception;

public class InvalidOperationError extends RuntimeException {
    public InvalidOperationError(String message) {
        super(message);
    }
}
