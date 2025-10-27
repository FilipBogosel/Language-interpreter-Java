package model.state;

import model.statement.IStatement;

public interface IExecutionStack {
    void push(IStatement statement);
    IStatement pop();
    boolean isEmpty();
}
