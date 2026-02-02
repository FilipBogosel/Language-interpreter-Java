package model.statement;

import javafx.util.Pair;
import model.state.ISemaphoreTable;
import model.state.ProgramState;
import model.state.ISymbolTable;
import model.type.IType;
import model.type.IntType;
import model.value.IntValue;
import model.value.IValue;
import model.exception.DifferentTypesExpressionError;
import model.exception.VariableNotDefinedError;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public record AcquireStatement(String variable) implements IStatement {
    private static final Lock lock = new ReentrantLock();

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        try {
            if (!state.symbolTable().isDefined(variable)) {
                throw new VariableNotDefinedError("Semaphore variable not defined");
            }

            IValue val = state.symbolTable().getValue(variable);
            if (!val.getType().equals(IntType.INSTANCE)) {
                throw new DifferentTypesExpressionError("Semaphore variable is not int");
            }

            int foundIndex = ((IntValue) val).value();
            ISemaphoreTable semaphoreTable = state.semaphoreTable();

            if (!semaphoreTable.containsKey(foundIndex)) {
                throw new VariableNotDefinedError("Semaphore index not found in table");
            }

            Pair<Integer, List<Integer>> entry = semaphoreTable.get(foundIndex);
            int n1 = entry.getKey();
            List<Integer> list1 = entry.getValue();
            int nl = list1.size();

            if (n1 > nl) {
                if (!list1.contains(state.id())) {
                    list1.add(state.id());
                    // Update table isn't strictly necessary if list is mutable, but good practice
                }
            } else {
                // Wait - push back to stack
                state.executionStack().push(this);
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AcquireStatement(variable);
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        if (typeEnvironment.getValue(variable).equals(IntType.INSTANCE)) {
            return typeEnvironment;
        } else {
            throw new DifferentTypesExpressionError("Acquire variable must be int");
        }
    }

    @Override
    public String toString() {
        return "acquire(" + variable + ")";
    }
}