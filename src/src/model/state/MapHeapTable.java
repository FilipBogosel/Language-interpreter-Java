package model.state;

import model.value.IValue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapHeapTable implements IHeapTable{
    private final Map<Integer, IValue> heapTable = new ConcurrentHashMap<>();
    private final AtomicInteger nextFreeAddress = new AtomicInteger(1);

    @Override
    public int allocate(IValue value) {
        int address = nextFreeAddress.get();
        this.heapTable.put(address, value);
        return nextFreeAddress.getAndIncrement();
    }

    @Override
    public IValue getValue(int address) {
        return heapTable.get(address);
    }

    @Override
    public void updateValue(int address, IValue value) {
        heapTable.put(address, value);
    }

    @Override
    public void free(int address) {
        heapTable.remove(address);
    }

    @Override
    public Map<Integer, IValue> getContent() {
        return new HashMap<>(heapTable);
    }

    @Override
    public void setContent(Map<Integer, IValue> content) {
        this.heapTable.clear();
        this.heapTable.putAll(content);
    }

    @Override
    public boolean isDefined(int address) {
        return this.heapTable.containsKey(address);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Heap Table:\n");
        if (heapTable.isEmpty()) {
            return "Heap Table: empty\n";
        }
        for (var pair : heapTable.entrySet()){
            sb.append("  ").append(pair.getKey()).append(" -> ").append(pair.getValue()).append("\n");
        }
        return sb.toString();
    }
}
