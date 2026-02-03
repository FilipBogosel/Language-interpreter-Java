package model.state;

import javafx.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BarrierTable implements IBarrierTable {
    private Map<Integer, Pair<Integer, List<Integer>>> map;
    private final AtomicInteger freeLocation;

    public BarrierTable() {
        this.map = new ConcurrentHashMap<>();
        this.freeLocation = new AtomicInteger(0);
    }

    @Override
    public synchronized void put(int key, Pair<Integer, List<Integer>> value) {
        map.put(key, value);
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> get(int key) {
        return map.get(key);
    }

    @Override
    public synchronized boolean containsKey(int key) {
        return map.containsKey(key);
    }

    @Override
    public synchronized int getFreeAddress() {
        return freeLocation.incrementAndGet();
    }

    @Override
    public synchronized void update(int key, Pair<Integer, List<Integer>> value) {
        map.put(key, value);
    }

    @Override
    public synchronized void setContent(Map<Integer, Pair<Integer, List<Integer>>> content) {
        this.map = content;
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        return map;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}