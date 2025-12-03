package model.statement;

import model.state.ProgramState;

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
    public String toString(){
        return "NoOperationStatement";
    }


}
