package model.state;

import java.util.Map;

public interface ILockTable {
    int getFreeValue();
    void put(int key, int value);
    int get(int key);
    boolean containsKey(int key);
    void update(int key, int value);
    void setContent(Map<Integer, Integer> content);
    Map<Integer, Integer> getContent();
}