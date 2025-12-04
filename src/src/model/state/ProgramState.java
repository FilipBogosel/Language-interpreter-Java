package model.state;

import model.exception.EmptyExecutionStackError;
import model.statement.IStatement;
import model.value.IValue;

public record ProgramState(ISymbolTable<String, IValue> symbolTable,
                           MyIStack<IStatement> executionStack,
                           MyIList<IValue> outputList,
                           IFileTable fileTable,
                           IHeapTable heapTable,
                           IStatement originalProgram,
                           int id) {
    public static int lastId = 0;

    public ProgramState(ISymbolTable<String, IValue> symbolTable,
                        MyIStack<IStatement> executionStack,
                        MyIList<IValue> outputList,
                        IFileTable fileTable,
                        IHeapTable heapTable,
                        IStatement originalProgram, int id) {
        this.symbolTable = symbolTable;
        this.executionStack = executionStack;
        this.outputList = outputList;
        this.originalProgram = originalProgram.deepCopy();
        this.executionStack.push(this.originalProgram);
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.id = id;
    }

    @Override
    public String toString() {
        return "=== Program State Id: " + this.id +  "===\n" +
                executionStack.toString() + "\n" +
                symbolTable.toString() + "\n" +
                outputList.toString() + "\n"+
                fileTable.toString() + "\n"+
                heapTable.toString() + "\n" +
                "====================\n";
    }

    public static synchronized int getAndIncrementLastId(){
        return lastId++;
    }

    public boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    public ProgramState oneStep() {
        if(executionStack.isEmpty()){
            throw new EmptyExecutionStackError("Error in oneStep: executionStack is empty");
        }
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }
}
