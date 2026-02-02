package model.state;

import javafx.util.Pair;
import java.util.List;
import java.util.Map;

public interface ISemaphoreTable {
    void put(int key, Pair<Integer, List<Integer>> value);
    Pair<Integer, List<Integer>> get(int key);
    boolean containsKey(int key);
    int getFreeAddress();
    void update(int key, Pair<Integer, List<Integer>> value);
    void setContent(Map<Integer, Pair<Integer, List<Integer>>> content);
    Map<Integer, Pair<Integer, List<Integer>>> getContent();
}