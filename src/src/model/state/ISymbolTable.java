package model.state;

import model.type.IType;
import model.value.IValue;

import java.util.Map;

public interface ISymbolTable<T1,T2> extends MyIDictionary<T1,T2> {
    IType getType(T1 variableName);
    void declareVariable(T1 variableName, IType type);
    Map<String, IValue> getContent();
}
