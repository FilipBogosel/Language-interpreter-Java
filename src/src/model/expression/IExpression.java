package model.expression;

import model.state.ISymbolTable;
import model.value.IValue;

public interface IExpression {
    public IValue evaluate(ISymbolTable<String,IValue> symbolTable);
    public IExpression deepCopy();
}
