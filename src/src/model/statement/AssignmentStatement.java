package model.statement;

import model.exception.DifferentTypesExpressionError;
import model.exception.VariableNotDefinedError;
import model.expression.IExpression;
import model.state.ProgramState;
import model.state.ISymbolTable;
import model.type.IType;
import model.value.IValue;


public record AssignmentStatement(String variableName, IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        ISymbolTable<String, IValue> symbolTable = state.symbolTable();
        if (!symbolTable.isDefined(variableName)){
            throw new VariableNotDefinedError(variableName);
        }

        var value = expression.evaluate(symbolTable, state.heapTable());
        var variableType = symbolTable.getType(variableName);
        var valueType = value.getType();

        if(!variableType.equals(valueType)){
            throw new DifferentTypesExpressionError("Variable type different from Expression assigned");
        }

        symbolTable.setValue(variableName, value);

        return null;

    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(variableName, expression.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        IType variableType = typeEnvironment.getValue(variableName);
        IType expressionType = expression.typecheck(typeEnvironment);
        if (variableType.equals(expressionType)) {
            return typeEnvironment;
        }
        else {
            throw new DifferentTypesExpressionError("Assignment: right hand side and left hand side have different types ");
        }
    }

    @Override
    public String toString() {
        return variableName + " = " + expression;
    }
}
