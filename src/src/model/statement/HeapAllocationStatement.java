package model.statement;

import model.exception.DifferentTypesExpressionError;
import model.exception.VariableNotDefinedError;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public record HeapAllocationStatement(String variableName, IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        IValue value = expression.evaluate(state.symbolTable(),state.heapTable());
        if(!state.symbolTable().isDefined(variableName)){
            throw new VariableNotDefinedError(variableName);
        }
        IType variableType = state.symbolTable().getType(variableName);
        if (!(variableType instanceof RefType)) {
            throw new DifferentTypesExpressionError("Variable " + variableName + " is not of type RefType");
        }
        IType innerType = ((RefType) variableType).innerType();
        if (!value.getType().equals(innerType)) {
            throw new DifferentTypesExpressionError("Type mismatch in HeapAllocationStatement");
        }
        int address = state.heapTable().allocate(value);
        state.symbolTable().setValue(variableName, new RefValue(address, innerType));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocationStatement(variableName, expression.deepCopy());
    }
    @Override
    public String toString(){
        return "new("+variableName+","+expression+")";
    }
}
