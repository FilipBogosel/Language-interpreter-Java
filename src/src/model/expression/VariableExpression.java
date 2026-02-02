package model.expression;

import model.exception.VariableNotDefinedError;
import model.state.IHeapTable;
import model.state.ISymbolTable;
import model.type.IType;
import model.value.IValue;

public record VariableExpression(String variableName) implements IExpression{
    @Override
    public IValue evaluate(ISymbolTable<String,IValue> symbolTable, IHeapTable heapTable) throws VariableNotDefinedError {
        if(!symbolTable.isDefined(variableName)){
            throw new VariableNotDefinedError(variableName);
        }
        return symbolTable.getValue(variableName);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(variableName);
    }

    @Override
    public IType typecheck(ISymbolTable<String, IType> typeEnvironment) {
        return typeEnvironment.getValue(variableName);
    }

    @Override
    public IExpression getNegation() {
        return null;
    }

    @Override
    public String toString() {
        return variableName;
    }
}
