package model.statement;

import model.expression.IExpression;
import model.state.ProgramState;

public record PrintStatement(IExpression expression) implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) {
        var value = expression.evaluate(state.symbolTable(), state.heapTable());
        state.outputList().add(value);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + expression + ")";
    }

}
