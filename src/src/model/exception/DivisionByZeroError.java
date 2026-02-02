package model.exception;

public class DivisionByZeroError extends RuntimeException {
    public DivisionByZeroError(String message) {
        super("Can't divide by zero in the Arithmetic innerExpression: " + message);
    }
}
