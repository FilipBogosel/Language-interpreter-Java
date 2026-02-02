package model.statement;

import model.state.ILockTable;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;
import model.type.IntType;
import model.value.IntValue;
import model.value.IValue;
import model.exception.DifferentTypesExpressionError;
import model.exception.VariableNotDefinedError;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public record LockStatement(String variable) implements IStatement {
    private static final Lock lock = new ReentrantLock();

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        try {
            IValue val = state.symbolTable().getValue(variable);
            int foundIndex = ((IntValue)val).value();
            ILockTable lockTable = state.lockTable();

            if (!lockTable.containsKey(foundIndex)) {
                throw new VariableNotDefinedError("Lock index not found");
            }

            // -1 means it is free
            if (lockTable.get(foundIndex) == -1) {
                // Acquire the lock by setting it to current state ID
                lockTable.update(foundIndex, state.id());
            } else {
                // Lock is busy, push back to stack to retry later
                state.executionStack().push(this);
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new LockStatement(variable);
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        if (typeEnvironment.getValue(variable).equals(IntType.INSTANCE))
            return typeEnvironment;
        else
            throw new DifferentTypesExpressionError("Variable must be of type int");
    }

    @Override
    public String toString() {
        return "lock(" + variable + ")";
    }
}