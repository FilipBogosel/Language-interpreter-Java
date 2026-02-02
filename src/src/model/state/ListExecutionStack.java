package model.state;

import java.util.ArrayList;
import java.util.Collections;
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
    public List<T> getContent() {
        return this.statements;
    }

    @Override
    public String toString() {
        if (statements.isEmpty()) {
            return "Execution Stack: empty\n";
        }

        StringBuilder sb = new StringBuilder("Execution Stack:\n");
        List<T> reversed = new ArrayList<>(statements);
        for (int i = 0; i < reversed.size(); i++) {
            sb.append("  [").append(i).append("] ").append(reversed.get(i)).append("\n");
        }
        return sb.toString();
    }

}

