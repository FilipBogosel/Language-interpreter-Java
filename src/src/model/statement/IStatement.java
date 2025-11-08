package model.statement;

import model.state.ProgramState;

import java.io.IOException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws IOException;

    IStatement deepCopy();
}
