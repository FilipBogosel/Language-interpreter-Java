package model.state;

import java.util.LinkedList;
import java.util.List;

public class ListExecutionStack<T> implements MyIStack<T> {
    private final List<T> statements = new LinkedList<>();

    @Override
    public void push(T statement) {
        statements.addFirst(statement);
    }

    @Override
    public T pop() {
        return this.statements.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return this.statements.isEmpty();
    }

    @Override
    public T top() {
        return this.statements.getFirst();
    }

    @Override
    public String toString() {
        return "ListExecutionStack{" +
                "statements=" + statements +
                '}';
    }
}

//TODO: add the deep copy for the program state. Make the deepCopy method in all the interfaces or override the clone method from the Object class
