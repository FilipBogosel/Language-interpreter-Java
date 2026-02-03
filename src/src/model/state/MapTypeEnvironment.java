package model.state;

import model.type.IType;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MapTypeEnvironment implements ISymbolTable<String, IType>{
    Map<String, IType> symbolTable = new HashMap<>();
    @Override
    public void setValue(String variableName, IType value) {
        symbolTable.put(variableName, value);
    }

    @Override
    public boolean isDefined(String variableName) {
        return symbolTable.containsKey(variableName);
    }

    @Override
    public IType getType(String variableName) {
        return symbolTable.get(variableName);
    }
    @Override
    public void declareVariable(String variableName, IType type) {
        return;
    }

    @Override
    public Map<String, IType> getContent() {
        return new HashMap<>(symbolTable);
    }

    @Override
    public ISymbolTable<String, IType> deepCopy() {
        MapTypeEnvironment copy = new MapTypeEnvironment();
        for(var variables : symbolTable.entrySet()){
            copy.setValue(variables.getKey(), variables.getValue());
        }
        return copy;
    }


    @Override
    public IType getValue(String variableName) {
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
