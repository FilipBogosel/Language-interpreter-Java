package model.exception;

public class FileAlreadyOpenedError extends RuntimeException {
    public FileAlreadyOpenedError(String message) {
        super(message);
    }
}
