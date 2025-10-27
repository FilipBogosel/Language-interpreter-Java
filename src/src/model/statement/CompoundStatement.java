package model.statement;

import model.state.ProgramState;


public record CompoundStatement(IStatement statementFirst, IStatement statementSecond) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        state.executionStack().push(statementSecond);
        state.executionStack().push(statementFirst);
        return state;
    }



}
