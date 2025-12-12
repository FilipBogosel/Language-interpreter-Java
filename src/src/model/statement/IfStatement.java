package model.statement;

import model.exception.DifferentTypesExpressionError;
import model.expression.IExpression;
import model.state.ProgramState;
import model.state.ISymbolTable;
import model.type.BooleanType;
import model.type.IType;
import model.value.BooleanValue;
import model.value.IValue;

public record IfStatement(IExpression condition, IStatement thenStatement,
                          IStatement elseStatement) implements IStatement {


    @Override
    public ProgramState execute(ProgramState state) {
        ISymbolTable<String, IValue> symbolTable = state.symbolTable();
        IValue value = condition.evaluate(symbolTable, state.heapTable());

        if (!(value.getType() instanceof BooleanType)) {
            throw new RuntimeException("If statement only accepts boolean types");
        }

        BooleanValue booleanValue = (BooleanValue) value;

        if (booleanValue.value()) {
            state.executionStack().push(thenStatement);
        }
        else {
            state.executionStack().push(elseStatement);
        }

        return null;
    }
    @Override
    public IStatement deepCopy() {
        return new IfStatement(condition.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        IType conditionType = condition.typecheck(typeEnvironment);
        if (!conditionType.equals(BooleanType.INSTANCE)) {
            throw new DifferentTypesExpressionError("If statement only accepts boolean types");
        }
        else {
            //we'll check both branches with a copy of the original type environment
            //because we don't want to modify the original one
            thenStatement.typecheck(typeEnvironment.deepCopy());
            elseStatement.typecheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        }
    }

    @Override
    public String toString() {
        return "if (" + condition + ") then { " +
                thenStatement + " } else { " +
                elseStatement + " }";
    }

}
