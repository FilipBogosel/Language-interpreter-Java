package model.statement;

import model.expression.IExpression;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.BooleanType;
import model.type.IType;
import model.exception.DifferentTypesExpressionError;

public record RepeatUntilStatement(IStatement statement, IExpression expression) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {

        IStatement converted = new CompoundStatement(
                statement,
                new WhileStatement(
                        expression.getNegation(), // Use the getNegation() method!
                        statement
                )
        );

        state.executionStack().push(converted);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new RepeatUntilStatement(statement.deepCopy(), expression.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        IType type = expression.typecheck(typeEnvironment);
        if (type.equals(BooleanType.INSTANCE)) {
            statement.typecheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        } else {
            throw new DifferentTypesExpressionError("RepeatUntil condition must be boolean");
        }
    }

    @Override
    public String toString() {
        return "repeat (" + statement + ") until (" + expression + ")";
    }
}
