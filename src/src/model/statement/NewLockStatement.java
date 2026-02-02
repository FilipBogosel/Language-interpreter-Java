package model.statement;

import model.state.ILockTable;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;
import model.type.IntType;
import model.value.IntValue;
import model.exception.DifferentTypesExpressionError;
import model.exception.VariableNotDefinedError;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public record NewLockStatement(String variable) implements IStatement {
    private static final Lock lock = new ReentrantLock();

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        try {
            ILockTable lockTable = state.lockTable();
            int freeLocation = lockTable.getFreeValue();

            // Put -1 (unlocked) at the new location
            lockTable.put(freeLocation, -1);

            // Update symbol table
            if (state.symbolTable().isDefined(variable) && state.symbolTable().getType(variable).equals(IntType.INSTANCE)) {
                state.symbolTable().setValue(variable, new IntValue(freeLocation));
            } else {
                throw new VariableNotDefinedError("Variable " + variable + " not declared or not int");
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewLockStatement(variable);
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
        return "newLock(" + variable + ")";
    }
}