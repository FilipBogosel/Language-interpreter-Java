package model.statement;

import model.expression.IExpression;
import model.expression.RelationalExpression;
import model.expression.VariableExpression;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;
import model.type.IntType;
import model.exception.DifferentTypesExpressionError;

public record ForStatement(String variable, IExpression expression1, IExpression expression2, IExpression expression3, IStatement statement) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        IStatement convertedStatement = new CompoundStatement(
                new VariableDeclarationStatement(IntType.INSTANCE, variable),
                new CompoundStatement(
                        new AssignmentStatement(variable, expression1),
                        new WhileStatement(
                                new RelationalExpression(new VariableExpression(variable)
                                        , expression2, 1),
                                new CompoundStatement(statement
                                        , new AssignmentStatement(variable, expression3))
                        )
                )
        );

        state.executionStack().push(convertedStatement);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ForStatement(variable, expression1.deepCopy(), expression2.deepCopy(), expression3.deepCopy(), statement.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        // Create new environment with the loop variable declared
        ISymbolTable<String, IType> newEnv = typeEnvironment.deepCopy();
        newEnv.setValue(variable, IntType.INSTANCE);

        // Type-check all expressions in the new environment
        IType t1 = expression1.typecheck(newEnv);
        IType t2 = expression2.typecheck(newEnv);
        IType t3 = expression3.typecheck(newEnv);

        if (t1.equals(IntType.INSTANCE) && t2.equals(IntType.INSTANCE) && t3.equals(IntType.INSTANCE)) {
            statement.typecheck(newEnv);

            return typeEnvironment;
        } else {
            throw new DifferentTypesExpressionError("For statement expects integer expressions");
        }
    }

    @Override
    public String toString() {
        return "for(" + variable + "=" + expression1 + "; " + variable + "<" + expression2 + "; "
                + variable + "=" + expression3 + ") {" + statement + "}";
    }
}