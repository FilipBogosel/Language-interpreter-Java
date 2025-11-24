package model.state;

import model.value.IValue;

import java.util.Map;

public interface IHeapTable {
//    int getNextFreeAddress();
    int allocate(IValue value);
    IValue getValue(int address);
    void updateValue(int address, IValue value);
    void free(int address);
    Map<Integer, IValue> getContent();
    void setContent(Map<Integer, IValue> content);
    boolean isDefined(int address);


}
