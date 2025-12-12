package model.statement;


import model.exception.VariableAlreadyDefined;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;

public record VariableDeclarationStatement(IType variableType, String variableName) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        var defined = state.symbolTable().isDefined(variableName);
        if (defined) {
            throw new VariableAlreadyDefined("Variable already defined "+variableName);
        }

        state.symbolTable().declareVariable(variableName, variableType);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclarationStatement(variableType,variableName);
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        typeEnvironment.setValue(variableName, variableType);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return variableType + " " + variableName;
    }
}
