package model.exception;

public class VariableAlreadyDefined extends RuntimeException {
    public VariableAlreadyDefined(String message) {
        super("Variable already defined "+message);
    }
}
