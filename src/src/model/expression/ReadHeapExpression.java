package model.expression;

import model.exception.DifferentTypesExpressionError;
import model.state.IHeapTable;
import model.state.ISymbolTable;
import model.type.RefType;
import model.value.IValue;

public record ReadHeapExpression(IExpression expression) implements IExpression {
    @Override
    public IValue evaluate(ISymbolTable<String, IValue> symbolTable, IHeapTable heapTable) {
        var value = expression.evaluate(symbolTable, heapTable);
        if(!(value.getType() instanceof RefType)){
            throw new DifferentTypesExpressionError("ReadHeapExpression: expression is not of RefType");
        }
        return null;
    }

    @Override
    public IExpression deepCopy() {
        return null;
    }
}
