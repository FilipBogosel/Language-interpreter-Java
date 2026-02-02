package model.statement;

import model.expression.IExpression;
import model.expression.RelationalExpression;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;
import model.exception.DifferentTypesExpressionError;

public record SwitchStatement(IExpression expression, IExpression pattern1, IStatement statement1, IExpression pattern2, IStatement statement2, IStatement defaultStatement) implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        // if (exp == exp1) then stmt1 else (if (exp == exp2) then stmt2 else stmt3)
        IStatement converted = new IfStatement(
                new RelationalExpression(expression, pattern1, 3), // 3 is "=="
                statement1,
                new IfStatement(
                        new RelationalExpression(expression, pattern2, 3),
                        statement2,
                        defaultStatement
                )
        );

        state.executionStack().push(converted);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SwitchStatement(expression.deepCopy(), pattern1.deepCopy(),
                                    statement1.deepCopy(), pattern2.deepCopy(),
                                    statement2.deepCopy(), defaultStatement.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        IType expType = expression.typecheck(typeEnvironment);
        IType p1Type = pattern1.typecheck(typeEnvironment);
        IType p2Type = pattern2.typecheck(typeEnvironment);

        if (expType.equals(p1Type) && expType.equals(p2Type)) {
            statement1.typecheck(typeEnvironment.deepCopy());
            statement2.typecheck(typeEnvironment.deepCopy());
            defaultStatement.typecheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        } else {
            throw new DifferentTypesExpressionError("Switch innerExpression types do not match");
        }
    }

    @Override
    public String toString() {
        return String.format("switch(%s) (case %s: %s) (case %s: %s) (default: %s)",
                expression, pattern1, statement1, pattern2, statement2, defaultStatement);
    }
}