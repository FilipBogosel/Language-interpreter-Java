package model.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListOutput<T> implements MyIList<T> {

    private final List<T> outputList =  Collections.synchronizedList(new ArrayList<>());
    @Override
    public void add(T value) {
        this.outputList.add(value);
    }

    @Override
    public String toString() {
        if (outputList.isEmpty()) {
            return "Output: empty\n";
        }

        StringBuilder sb = new StringBuilder("Output:\n");
        for (int i = 0; i < outputList.size(); i++) {
            sb.append("  [").append(i).append("] ").append(outputList.get(i)).append("\n");
        }
        return sb.toString();
    }
    @Override
    public List<T> getContent() {
        return outputList;
    }

}
