package model.state;

import model.statement.IStatement;
import model.value.IValue;

public record ProgramState(ISymbolTable<String, IValue> symbolTable, MyIStack<IStatement> executionStack,
                           MyIList<IValue> outputList, IStatement originalProgram) {
    public ProgramState(ISymbolTable<String, IValue> symbolTable,
                        MyIStack<IStatement> executionStack,
                        MyIList<IValue> outputList,
                        IStatement originalProgram) {
        this.symbolTable = symbolTable;
        this.executionStack = executionStack;
        this.outputList = outputList;

        this.originalProgram = originalProgram.deepCopy();
        this.executionStack.push(this.originalProgram);
    }

    @Override
    public String toString() {
        return "ProgramState{" +
                "symbolTable=" + symbolTable +
                ", executionStack=" + executionStack +
                ", outputList=" + outputList +
                '}';
    }
}
