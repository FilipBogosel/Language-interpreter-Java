package model.exception;

public class EmptyExecutionStackError extends RuntimeException {
    public EmptyExecutionStackError(String message) {
        super("Execution stack empty! "+message);
    }
}
