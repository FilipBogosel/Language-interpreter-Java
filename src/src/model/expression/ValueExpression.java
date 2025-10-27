package model.expression;

import model.state.ISymbolTable;
import model.value.IValue;

public record ValueExpression(IValue value) implements IExpression {
    @Override
    public IValue evaluate(ISymbolTable symbolTable) {
        return this.value;
    }
}
