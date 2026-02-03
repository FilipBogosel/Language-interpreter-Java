package model.statement;

import model.exception.DifferentTypesExpressionError;
import model.expression.IExpression;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.BooleanType;
import model.type.IType;

public record ConditionalAssignmentStatement(String variableName, IExpression expression1, IExpression expression2, IExpression expression3) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        IStatement converted = new IfStatement(
                expression1,
                new AssignmentStatement(variableName, expression2),
                new AssignmentStatement(variableName, expression3)
                );
        state.executionStack().push(converted);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ConditionalAssignmentStatement(variableName, expression1.deepCopy(), expression2.deepCopy(), expression3.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        IType type1 = expression1.typecheck(typeEnvironment);
        IType type2 = expression2.typecheck(typeEnvironment);
        IType type3 = expression3.typecheck(typeEnvironment);
        if(!type1.equals(BooleanType.INSTANCE)) {
            throw new DifferentTypesExpressionError("Expression used for conditional assignment is not boolean!");
        }

        IType variableType = typeEnvironment.getType(variableName);

        if(!type2.equals(type3) || !variableType.equals(type2)) {
            throw new DifferentTypesExpressionError("Expressions used for conditional assignment don't have the same type!");
        }
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return variableName+"="+expression1+"?"+expression2+":"+expression3;
    }
}
