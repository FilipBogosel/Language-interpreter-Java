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

public record UnlockStatement(String variable) implements IStatement {
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

            // Only unlock if the current thread owns it
            if (lockTable.get(foundIndex) == state.id()) {
                lockTable.update(foundIndex, -1);
            }
            // else do nothing
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new UnlockStatement(variable);
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        if (typeEnvironment.getType(variable).equals(IntType.INSTANCE))
            return typeEnvironment;
        else
            throw new DifferentTypesExpressionError("Variable must be of type int");
    }

    @Override
    public String toString() {
        return "unlock(" + variable + ")";
    }
}