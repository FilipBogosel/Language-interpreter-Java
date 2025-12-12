package model.statement;

import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;

import java.io.IOException;

public interface IStatement {
    ProgramState execute(ProgramState state) ;
    IStatement deepCopy();
    ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment);
}
