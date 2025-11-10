package model.state;
import model.exception.CloseNonOpenedFileError;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class MapFileTable implements IFileTable {
    Map<String,BufferedReader> fileTable = new HashMap<>();

    @Override
    public void openFile(String variableName, BufferedReader reader) {
        fileTable.put(variableName,reader);
    }

    @Override
    public boolean isDefined(String variableName) {
        return fileTable.containsKey(variableName);
    }

    @Override
    public BufferedReader getFilePointer(String variableName) {
        return fileTable.get(variableName);
    }

    @Override
    public void closeFile(String variableName) {
        try{
            fileTable.remove(variableName);
        }
        catch(Exception e){
            throw new CloseNonOpenedFileError("Error closing file: " + variableName);
        }
    }

    @Override
    public String toString() {
        if (fileTable.isEmpty()) {
            return "File Table: empty\n";
        }

        StringBuilder sb = new StringBuilder("File Table:\n");
        fileTable.forEach((key, value) ->
                sb.append("  ").append(key).append(" -> ").append(value).append("\n")
        );
        return sb.toString();
    }
}
