package model.state;

import model.statement.IStatement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListExecutionStack implements IExecutionStack {
    private final List<IStatement> statements = new LinkedList<>();


    @Override
    public void push(IStatement statement) {
        statements.addFirst(statement);
    }

    @Override
    public IStatement pop() {
        return this.statements.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return this.statements.isEmpty();
    }

    @Override
    public String toString() {
        return "ListExecutionStack{" +
                "statements=" + statements +
                '}';
    }
}

//TODO: make the interfaces for the program state ADTs use generic types
//TODO: add the deep copy. Make the deepCopy method in all the interfaces or override the clone method from the Object class
