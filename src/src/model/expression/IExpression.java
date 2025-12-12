package model.expression;

import model.state.IHeapTable;
import model.state.ISymbolTable;
import model.type.IType;
import model.value.IValue;

public interface IExpression {
    public IValue evaluate(ISymbolTable<String,IValue> symbolTable, IHeapTable heapTable);
    public IExpression deepCopy();
    public IType typecheck(ISymbolTable<String,IType> typeEnvironment);
}
