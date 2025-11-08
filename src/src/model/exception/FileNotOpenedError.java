package model.exception;

public class FileNotOpenedError extends RuntimeException {
    public FileNotOpenedError(String message) {
        super(message);
    }
}
