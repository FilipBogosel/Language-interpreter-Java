package model.state;

import model.type.IType;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MapSymbolTable implements ISymbolTable {
    Map<String, IValue> symbolTable = new HashMap<>();
    @Override
    public void setValue(String variableName, IValue value) {
        symbolTable.put(variableName, value);
    }

    @Override
    public boolean isDefined(String variableName) {
        return symbolTable.containsKey(variableName);
    }

    @Override
    public IType getType(String variableName) {
        return symbolTable.get(variableName).getType();
    }

    @Override
    public void declareVariable(String variableName, IType type) {
        symbolTable.put(variableName, type.getDefaultValue());
    }

    @Override
    public IValue getValue(String variableName) {
        return symbolTable.get(variableName);
    }

    @Override
    public String toString() {
        return "MapSymbolTable{" +
                "symbolTable=" + symbolTable +
                '}';
    }
}
