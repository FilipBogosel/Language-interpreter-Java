package model.state;

import java.util.List;

public interface MyIStack<T> {
    void push(T statement);
    T pop();
    boolean isEmpty();
    T top();
    List<T> getContent();
}
