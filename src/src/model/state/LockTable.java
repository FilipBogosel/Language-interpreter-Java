package model.state;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LockTable implements ILockTable {
    private Map<Integer, Integer> lockTable;
    private final AtomicInteger freeLocation;

    public LockTable() {
        this.lockTable = new ConcurrentHashMap<>();
        this.freeLocation = new AtomicInteger(1);
    }

    @Override
    public synchronized int getFreeValue() {
        return freeLocation.getAndIncrement();
    }

    @Override
    public synchronized void put(int key, int value) {
        lockTable.put(key, value);
    }

    @Override
    public synchronized int get(int key) {
        return lockTable.get(key);
    }

    @Override
    public synchronized boolean containsKey(int key) {
        return lockTable.containsKey(key);
    }

    @Override
    public synchronized void update(int key, int value) {
        lockTable.put(key, value);
    }

    @Override
    public synchronized void setContent(Map<Integer, Integer> content) {
        this.lockTable = content;
    }

    @Override
    public synchronized Map<Integer, Integer> getContent() {
        return lockTable;
    }

    @Override
    public String toString() {
        return lockTable.toString();
    }
}