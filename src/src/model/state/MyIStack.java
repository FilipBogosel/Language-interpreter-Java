package model.state;

public interface MyIStack<T> {
    void push(T statement);
    T pop();
    boolean isEmpty();
    T top();
}
