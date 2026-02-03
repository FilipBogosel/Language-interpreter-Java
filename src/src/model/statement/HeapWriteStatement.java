package model.statement;

import model.exception.DifferentTypesExpressionError;
import model.exception.VariableNotDefinedError;
import model.expression.IExpression;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public record HeapWriteStatement(String varName, IExpression expression) implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) {
        if(!state.symbolTable().isDefined(varName)){
            throw new VariableNotDefinedError(varName);
        }

        IValue refValue = state.symbolTable().getValue(varName);
        if (!(refValue instanceof RefValue)){
            throw new DifferentTypesExpressionError("HeapWriteStatement: Variable " + varName + " is not a RefType");
        }

        int heapAddress = ((RefValue) refValue).address();
        if(!state.heapTable().isDefined(heapAddress)){
            throw new VariableNotDefinedError("HeapWriteStatement: variable not defined at address " + heapAddress);
        }

        var value = expression.evaluate(state.symbolTable(),state.heapTable());

        IType pointerType = state.symbolTable().getType(varName);
        IType variableType = ((RefType) pointerType).innerType();

        if(!variableType.equals(value.getType()) && !pointerType.equals(new RefType(value.getType())) ){
            throw new DifferentTypesExpressionError("Type mismatch in HeapWriteStatement");
        }

        state.heapTable().updateValue(heapAddress, value);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new HeapWriteStatement(varName, expression.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        IType variableType = typeEnvironment.getType(varName);
        IType expType = expression.typecheck(typeEnvironment);
        if (variableType instanceof RefType(IType innerType)) {
            if (innerType.equals(expType)) {
                return typeEnvironment;
            }
            else {
                throw new DifferentTypesExpressionError("HeapWrite stmt: right hand side and left hand side have different types ");
            }
        }
        else {
            throw new DifferentTypesExpressionError("HeapWrite stmt: variable is not a RefType");
        }
    }

    @Override
    public String toString(){
        return "HeapWriteStatement"+"("+varName+","+expression+")";
    }
}