package model.statement;

import model.state.*;
import model.type.IType;

public record ForkStatement(IStatement innerStatement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        MyIStack<IStatement> childExecutionStack = new ListExecutionStack<>();
        int newId = ProgramState.getAndIncrementLastId();
        return new ProgramState(state.symbolTable().deepCopy(),
                childExecutionStack,
                state.outputList(),
                state.fileTable(),
                state.heapTable(),
                innerStatement,
                state.semaphoreTable(),
                state.lockTable(),
                state.barrierTable(),
                state.latchTable(),
                newId);
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(innerStatement.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        return innerStatement.typecheck(typeEnvironment.deepCopy());
    }

    @Override
    public String toString(){
        return "fork{"+innerStatement+"}";
    }
}