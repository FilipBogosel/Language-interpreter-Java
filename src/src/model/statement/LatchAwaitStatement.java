package model.statement;

import model.state.ILatchTable;
import model.state.ProgramState;
import model.state.ISymbolTable;
import model.type.IType;
import model.type.IntType;
import model.value.IntValue;
import model.value.IValue;
import model.exception.DifferentTypesExpressionError;
import model.exception.VariableNotDefinedError;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public record LatchAwaitStatement(String variable) implements IStatement {
    private static final Lock lock = new ReentrantLock();

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        try {
            if (!state.symbolTable().isDefined(variable)) throw new VariableNotDefinedError("Variable not defined");

            IValue val = state.symbolTable().getValue(variable);
            int foundIndex = ((IntValue)val).value();
            ILatchTable latchTable = state.latchTable();

            if (!latchTable.containsKey(foundIndex)) throw new VariableNotDefinedError("Latch index not found");

            if (latchTable.get(foundIndex) > 0) {
                state.executionStack().push(this); // Wait (busy wait)
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new LatchAwaitStatement(variable);
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        if (typeEnvironment.getType(variable).equals(IntType.INSTANCE)) return typeEnvironment;
        else throw new DifferentTypesExpressionError("Variable must be int");
    }

    @Override
    public String toString() {
        return "await(" + variable + ")";
    }
}