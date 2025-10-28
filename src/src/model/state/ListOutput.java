package model.state;

import java.util.ArrayList;
import java.util.List;

public class ListOutput<T> implements MyIList<T> {

    private final List<T> outputList = new ArrayList<>();
    @Override
    public void add(T value) {
        this.outputList.add(value);
    }

    @Override
    public String toString() {
        return "ListOut{" + "values=" + outputList + '}';
    }
}
