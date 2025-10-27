package model.exception;

public class DifferentTypesExpressionError extends RuntimeException {
    public DifferentTypesExpressionError(String message) {
        super(message);
    }
}
