package model.statement;


import model.exception.VariableAlreadyDefined;
import model.state.ProgramState;
import model.type.IType;
import model.value.IValue;

public record VariableDeclarationStatement(IType variableType, String variableName) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        var defined = state.symbolTable().isDefined(variableName);
        if (defined) {
            throw new VariableAlreadyDefined("Variable already defined "+variableName);
        }

        state.symbolTable().declareVariable(variableName, variableType);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(variableType,variableName);
    }

    @Override
    public String toString() {
        return variableType + " " + variableName;
    }
}
