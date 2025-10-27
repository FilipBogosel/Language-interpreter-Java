package model.statement;

import model.state.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state);
}
