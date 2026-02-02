package model.expression;

import model.exception.DifferentTypesExpressionError;
import model.exception.VariableNotDefinedError;
import model.state.IHeapTable;
import model.state.ISymbolTable;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public record ReadHeapExpression(IExpression expression) implements IExpression {
    @Override
    public IValue evaluate(ISymbolTable<String, IValue> symbolTable, IHeapTable heapTable) {
        var value = expression.evaluate(symbolTable, heapTable);
        if(!(value.getType() instanceof RefType)){
            throw new DifferentTypesExpressionError("ReadHeapExpression: innerExpression is not of RefType");
        }
        RefValue refValue = (RefValue) value;
        int address = refValue.address();
        if(!heapTable.isDefined(address)){
            throw new VariableNotDefinedError("ReadHeapExpression: variable not defined at address " + address);
        }
        return heapTable.getValue(address);
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }

    @Override
    public IType typecheck(ISymbolTable<String, IType> typeEnvironment) {
        IType typ=expression.typecheck(typeEnvironment);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.innerType();
        } else
            throw new DifferentTypesExpressionError("the rH argument is not a Ref Type");
    }

    @Override
    public IExpression getNegation() {
        return null;
    }

    @Override
    public String toString() {
        return "readHeap(" + expression + ")";
    }
}
