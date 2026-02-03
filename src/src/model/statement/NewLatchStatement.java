package model.statement;

import model.expression.IExpression;
import model.state.ILatchTable;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;
import model.type.IntType;
import model.value.IntValue;
import model.value.IValue;
import model.exception.DifferentTypesExpressionError;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public record NewLatchStatement(String variable, IExpression expression) implements IStatement {
    private static final Lock lock = new ReentrantLock();

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        try {
            IValue num1 = expression.evaluate(state.symbolTable(), state.heapTable());
            if (!num1.getType().equals(IntType.INSTANCE)) {
                throw new DifferentTypesExpressionError("Latch expression must be int");
            }
            int number = ((IntValue) num1).value();
            ILatchTable latchTable = state.latchTable();
            int freeAddr = latchTable.getFreeAddress();

            latchTable.put(freeAddr, number);

            if (state.symbolTable().isDefined(variable) && state.symbolTable().getType(variable).equals(IntType.INSTANCE)) {
                state.symbolTable().setValue(variable, new IntValue(freeAddr));
            } else {
                throw new DifferentTypesExpressionError("Variable not defined or not int");
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NewLatchStatement(variable, expression.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        if (typeEnvironment.getType(variable).equals(IntType.INSTANCE) && expression.typecheck(typeEnvironment).equals(IntType.INSTANCE)) {
            return typeEnvironment;
        } else {
            throw new DifferentTypesExpressionError("Latch type check failed");
        }
    }

    @Override
    public String toString() {
        return "newLatch(" + variable + ", " + expression + ")";
    }
}