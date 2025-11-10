package model.state;

import model.statement.IStatement;
import model.value.IValue;

public record ProgramState(ISymbolTable<String, IValue> symbolTable, MyIStack<IStatement> executionStack,
                           MyIList<IValue> outputList, IStatement originalProgram, IFileTable fileTable) {
    public ProgramState(ISymbolTable<String, IValue> symbolTable,
                        MyIStack<IStatement> executionStack,
                        MyIList<IValue> outputList,
                        IStatement originalProgram,
                        IFileTable fileTable) {
        this.symbolTable = symbolTable;
        this.executionStack = executionStack;
        this.outputList = outputList;
        this.originalProgram = originalProgram.deepCopy();
        this.executionStack.push(this.originalProgram);
        this.fileTable = fileTable;
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
