package model.state;

import model.type.IType;
import model.value.IValue;

import java.io.BufferedReader;

public interface IFileTable {
    void openFile(String variableName, BufferedReader reader);
    boolean isDefined(String variableName);
    BufferedReader getFilePointer(String variableName);
}
