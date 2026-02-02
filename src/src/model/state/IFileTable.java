package model.state;

import model.type.IType;
import model.value.IValue;

import java.io.BufferedReader;
import java.util.Map;

public interface IFileTable {
    void openFile(String variableName, BufferedReader reader);
    boolean isDefined(String variableName);
    BufferedReader getFilePointer(String variableName);
    void closeFile(String variableName);
    Map<String, BufferedReader>  getContent();
}
