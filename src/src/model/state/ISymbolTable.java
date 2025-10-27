package model.state;

import model.type.IType;
import model.value.IValue;

public interface ISymbolTable {
    void setValue(String variableName, IValue value);
    boolean isDefined(String variableName);
    IType getType(String variableName);
    void declareVariable(String variableName, IType type);
    IValue getValue(String variableName);
}
