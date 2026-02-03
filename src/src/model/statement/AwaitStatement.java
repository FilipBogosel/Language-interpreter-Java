package model.statement;

import javafx.util.Pair;
import model.state.IBarrierTable;
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

public record AwaitStatement(String variable) implements IStatement {
    private static final Lock lock = new ReentrantLock();

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        try {
            if (!state.symbolTable().isDefined(variable)) {
                throw new VariableNotDefinedError("Await variable not defined");
            }

            IValue val = state.symbolTable().getValue(variable);
            int foundIndex = ((IntValue) val).value();
            IBarrierTable barrierTable = state.barrierTable();

            if (!barrierTable.containsKey(foundIndex)) {
                throw new VariableNotDefinedError("Barrier index not found");
            }

            Pair<Integer, List<Integer>> entry = barrierTable.get(foundIndex);
            int N1 = entry.getKey();
            List<Integer> list1 = entry.getValue();
            int NL = list1.size();

            if (N1 > NL) {
                if (list1.contains(state.id())) {
                    state.executionStack().push(this); // Wait
                } else {
                    list1.add(state.id());
                    state.executionStack().push(this); // Wait
                }
            }
            // else: N1 <= NL (Barrier reached limit), so we do nothing and let execution proceed
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AwaitStatement(variable);
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        if (typeEnvironment.getType(variable).equals(IntType.INSTANCE)) {
            return typeEnvironment;
        } else {
            throw new DifferentTypesExpressionError("Await variable must be int");
        }
    }

    @Override
    public String toString() {
        return "await(" + variable + ")";
    }
}