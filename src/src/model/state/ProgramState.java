package model.state;

import model.statement.IStatement;
import model.value.IValue;

public record ProgramState(ISymbolTable<String, IValue> symbolTable, MyIStack<IStatement> executionStack,
                           MyIList<IValue> outputList, IFileTable fileTable, IHeapTable heapTable, IStatement originalProgram) {
    public ProgramState(ISymbolTable<String, IValue> symbolTable,
                        MyIStack<IStatement> executionStack,
                        MyIList<IValue> outputList,
                        IFileTable fileTable,
                        IHeapTable heapTable,
                        IStatement originalProgram) {
        this.symbolTable = symbolTable;
        this.executionStack = executionStack;
        this.outputList = outputList;
        this.originalProgram = originalProgram.deepCopy();
        this.executionStack.push(this.originalProgram);
        this.fileTable = fileTable;
        this.heapTable = heapTable;
    }

    @Override
    public String toString() {
        return "=== Program State ===\n" +
                executionStack.toString() + "\n" +
                symbolTable.toString() + "\n" +
                outputList.toString() + "\n"+
                fileTable.toString() + "\n"+
                "====================\n";
    }

}
