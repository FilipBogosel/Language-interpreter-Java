package model.state;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LatchTable implements ILatchTable {
    private Map<Integer, Integer> map;
    private final AtomicInteger freeLocation;

    public LatchTable() {
        this.map = new ConcurrentHashMap<>();
        this.freeLocation = new AtomicInteger(1);
    }

    @Override
    public synchronized void put(int key, int value) {
        map.put(key, value);
    }

    @Override
    public synchronized int get(int key) {
        return map.get(key);
    }

    @Override
    public synchronized boolean containsKey(int key) {
        return map.containsKey(key);
    }

    @Override
    public synchronized int getFreeAddress() {
        return freeLocation.getAndIncrement();
    }

    @Override
    public synchronized void update(int key, int value) {
        map.put(key, value);
    }

    @Override
    public synchronized void setContent(Map<Integer, Integer> content) {
        this.map = content;
    }

    @Override
    public synchronized Map<Integer, Integer> getContent() {
        return map;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}