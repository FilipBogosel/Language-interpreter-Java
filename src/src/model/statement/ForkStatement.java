package model.statement;

import model.state.IHeapTable;
import model.state.ListExecutionStack;
import model.state.MyIStack;
import model.state.ProgramState;

public record ForkStatement(IStatement innerStatement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        MyIStack<IStatement> childExecutionStack = new ListExecutionStack<>();
        int newId = ProgramState.getAndIncrementLastId();
        return new ProgramState(state.symbolTable().deepCopy(), childExecutionStack, state.outputList(),  state.fileTable(), state.heapTable(), innerStatement, newId);
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(innerStatement.deepCopy());
    }

    @Override
    public String toString(){
        return "fork{"+innerStatement+"}";
    }
}