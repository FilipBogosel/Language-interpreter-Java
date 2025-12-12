package model.state;

import model.type.IType;
import model.value.IValue;

import java.util.Map;

public interface ISymbolTable<T1,T2> {
    void setValue(T1 key, T2 value);
    boolean isDefined(T1 key);
    T2 getValue(T1 key);
    IType getType(T1 variableName);
    void declareVariable(T1 variableName, IType type);
    Map<T1, T2> getContent();
    ISymbolTable<T1,T2> deepCopy();

}
