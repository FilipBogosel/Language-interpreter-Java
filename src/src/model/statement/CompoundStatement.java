package model.statement;

import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;


public record CompoundStatement(IStatement statementFirst, IStatement statementSecond) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        state.executionStack().push(statementSecond);
        state.executionStack().push(statementFirst);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(statementFirst.deepCopy(), statementSecond.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        return statementSecond.typecheck(statementFirst.typecheck(typeEnvironment));
    }

    @Override
    public String toString() {
        return statementFirst + "; " + statementSecond;
    }

}
