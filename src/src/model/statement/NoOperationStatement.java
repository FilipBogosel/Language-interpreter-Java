package model.statement;

import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;

public class NoOperationStatement implements IStatement {

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NoOperationStatement();
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "NoOperationStatement";
    }


}
