package model.statement;

import model.expression.ValueExpression;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;
import model.value.IntValue;

public record WaitStatement(Integer number) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        if (number > 0) {
            state.executionStack().push(new WaitStatement(number-1));
            state.executionStack().push(
                    new PrintStatement(
                            new ValueExpression(
                                    new IntValue(number)
                            )));
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WaitStatement(number);
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "wait(" + number + ")";
    }
}
