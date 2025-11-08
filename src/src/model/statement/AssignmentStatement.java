package model.statement;

import model.exception.DifferentTypesExpressionError;
import model.exception.VariableNotDefinedError;
import model.expression.IExpression;
import model.state.ProgramState;
import model.state.ISymbolTable;
import model.value.IValue;


public record AssignmentStatement(String variableName, IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        ISymbolTable<String, IValue> symbolTable = state.symbolTable();
        if (!symbolTable.isDefined(variableName)){
            throw new VariableNotDefinedError(variableName);
        }

        var value = expression.evaluate(symbolTable);
        var variableType = symbolTable.getType(variableName);
        var valueType = value.getType();

        if(!variableType.equals(valueType)){
            throw new DifferentTypesExpressionError("Variable type different from Expression assigned");
        }

        symbolTable.setValue(variableName, value);

        return state;

    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return variableName + " = " + expression;
    }
}
