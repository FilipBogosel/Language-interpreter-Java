package model.state;

import java.util.List;

public interface MyIList<T> {
    void add(T value);
    public List<T> getContent();
}
