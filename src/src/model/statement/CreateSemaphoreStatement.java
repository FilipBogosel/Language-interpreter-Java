package model.statement;

import javafx.util.Pair;
import model.expression.IExpression;
import model.state.ISemaphoreTable;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;
import model.exception.DifferentTypesExpressionError;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public record CreateSemaphoreStatement(String variable, IExpression expression) implements IStatement {
    private static final Lock lock = new ReentrantLock();

    @Override
    public ProgramState execute(ProgramState state) {
        lock.lock();
        try {
            ISemaphoreTable semaphoreTable = state.semaphoreTable();
            IValue evaluatedExp = expression.evaluate(state.symbolTable(), state.heapTable());

            if (!evaluatedExp.getType().equals(IntType.INSTANCE)) {
                throw new DifferentTypesExpressionError("Semaphore capacity must be an integer");
            }

            int number1 = ((IntValue) evaluatedExp).value();
            int newFreeLocation = semaphoreTable.getFreeAddress();

            semaphoreTable.put(newFreeLocation, new Pair<>(number1, new ArrayList<>()));

            if (state.symbolTable().isDefined(variable) && state.symbolTable().getType(variable).equals(IntType.INSTANCE)) {
                state.symbolTable().setValue(variable, new IntValue(newFreeLocation));
            } else {
                throw new DifferentTypesExpressionError("Variable for semaphore not defined or not int");
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CreateSemaphoreStatement(variable, expression.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        if (typeEnvironment.getValue(variable).equals(IntType.INSTANCE) &&
                expression.typecheck(typeEnvironment).equals(IntType.INSTANCE)) {
            return typeEnvironment;
        } else {
            throw new DifferentTypesExpressionError("Semaphore type check failed");
        }
    }

    @Override
    public String toString() {
        return "createSemaphore(" + variable + ", " + expression + ")";
    }
}