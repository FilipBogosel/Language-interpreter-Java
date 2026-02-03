package model.statement;

import javafx.util.Pair;
import model.expression.IExpression;
import model.state.IBarrierTable;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;
import model.type.IntType;
import model.value.IntValue;
import model.value.IValue;
import model.exception.DifferentTypesExpressionError;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public record NewBarrierStatement(String variable, IExpression expression) implements IStatement {
    private static final Lock lock = new ReentrantLock();

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        try {
            IValue number = expression.evaluate(state.symbolTable(), state.heapTable());
            if (!number.getType().equals(IntType.INSTANCE)) {
                throw new DifferentTypesExpressionError("Barrier capacity must be int");
            }

            int nr = ((IntValue) number).value();
            IBarrierTable barrierTable = state.barrierTable();
            int newFreeLocation = barrierTable.getFreeAddress();

            barrierTable.put(newFreeLocation, new Pair<>(nr, new ArrayList<>()));

            if (state.symbolTable().isDefined(variable) && state.symbolTable().getType(variable).equals(IntType.INSTANCE)) {
                state.symbolTable().setValue(variable, new IntValue(newFreeLocation));
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
        return new NewBarrierStatement(variable, expression.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        if (typeEnvironment.getValue(variable).equals(IntType.INSTANCE) &&
                expression.typecheck(typeEnvironment).equals(IntType.INSTANCE)) {
            return typeEnvironment;
        } else {
            throw new DifferentTypesExpressionError("Barrier type check failed");
        }
    }

    @Override
    public String toString() {
        return "newBarrier(" + variable + ", " + expression + ")";
    }
}