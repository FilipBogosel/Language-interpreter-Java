package model.state;

import model.type.IType;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MapSymbolTable implements ISymbolTable<String,IValue> {
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
        IValue value = symbolTable.get(variableName);
        return value != null ? value.getType() : null;
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
        if (symbolTable.isEmpty()) {
            return "Symbol Table: empty\n";
        }

        StringBuilder sb = new StringBuilder("Symbol Table:\n");
        symbolTable.forEach((key, value) ->
                sb.append("  ").append(key).append(" -> ").append(value).append("\n")
        );
        return sb.toString();
    }

}
