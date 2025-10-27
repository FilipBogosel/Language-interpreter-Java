package model.state;

import model.value.IValue;

import java.util.ArrayList;
import java.util.List;

public class ListOutput implements IOutputList {

    private final List<IValue> outputList = new ArrayList<>();
    @Override
    public void add(IValue value) {
        this.outputList.add(value);
    }

    @Override
    public String toString() {
        return "ListOut{" + "values=" + outputList + '}';
    }
}
