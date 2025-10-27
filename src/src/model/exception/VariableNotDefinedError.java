package model.exception;

public class VariableNotDefinedError extends RuntimeException {
    public VariableNotDefinedError(String varName) {
        super(varName + " is not defined");
    }
}
