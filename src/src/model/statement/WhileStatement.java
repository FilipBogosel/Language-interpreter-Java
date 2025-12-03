package model.statement;

import model.exception.DifferentTypesExpressionError;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.BooleanType;
import model.value.BooleanValue;
import model.value.IValue;

public record WhileStatement(IExpression expression, IStatement statement) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {

        IValue value = expression.evaluate(state.symbolTable(), state.heapTable());

        if (!value.getType().equals(BooleanType.INSTANCE)) {
            throw new DifferentTypesExpressionError("Condition in 'while' statement must be of type boolean");
        }

        BooleanValue boolValue = (BooleanValue) value;

        if (boolValue.value()) {
            state.executionStack().push(this);
            state.executionStack().push(statement);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "while (" + expression + ") " + statement;
    }
}