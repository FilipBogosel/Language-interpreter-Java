package model.exception;

public class OpenFileError extends RuntimeException {
    public OpenFileError(String message) {
        super(message);
    }
}
