package model.state;

public record ProgramState(ISymbolTable symbolTable, IExecutionStack executionStack, IOutputList outputList) {

}
