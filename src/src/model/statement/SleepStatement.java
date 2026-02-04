package model.statement;

import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;
import model.type.IntType;

public record SleepStatement(Integer number) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        if(number > 0){
            state.executionStack().push(new SleepStatement(number-1));
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SleepStatement(number);
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "sleep(" + number + ")";
    }
}
