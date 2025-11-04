package model.statement;

import model.expression.IExpression;
import model.state.ProgramState;
import model.state.ISymbolTable;
import model.type.BooleanType;
import model.value.BooleanValue;
import model.value.IValue;

public record IfStatement(IExpression condition, IStatement thenStatement,
                          IStatement elseStatement) implements IStatement {


    @Override
    public ProgramState execute(ProgramState state) {
        ISymbolTable<String, IValue> symbolTable = state.symbolTable();
        IValue value = condition.evaluate(symbolTable);

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

        return state;
    }
    @Override
    public IStatement deepCopy() {
        return new IfStatement(condition.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "if (" + condition.toString() + ") then { " +
                thenStatement.toString() + " } else { " +
                elseStatement.toString() + " }";
    }

}
