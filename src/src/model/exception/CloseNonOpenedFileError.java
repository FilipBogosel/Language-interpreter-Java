package model.exception;

public class CloseNonOpenedFileError extends RuntimeException {
    public CloseNonOpenedFileError(String message) {
        super(message);
    }
}
