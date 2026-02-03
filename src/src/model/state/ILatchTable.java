package model.state;

import java.util.Map;

public interface ILatchTable {
    void put(int key, int value);
    int get(int key);
    boolean containsKey(int key);
    int getFreeAddress();
    void update(int key, int value);
    void setContent(Map<Integer, Integer> content);
    Map<Integer, Integer> getContent();
}