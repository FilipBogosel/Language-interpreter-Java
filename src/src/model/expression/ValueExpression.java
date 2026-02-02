package model.expression;

import model.state.IHeapTable;
import model.state.ISymbolTable;
import model.type.IType;
import model.value.IValue;

public record ValueExpression(IValue value) implements IExpression {
    @Override
    public IValue evaluate(ISymbolTable<String,IValue> symbolTable, IHeapTable heapTable) {
        return this.value;
    }

    @Override
    public IExpression deepCopy() {
        return new ValueExpression(value);
    }

    @Override
    public IType typecheck(ISymbolTable<String, IType> typeEnvironment) {
        return value.getType();
    }

    @Override
    public IExpression getNegation() {
        return null;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
