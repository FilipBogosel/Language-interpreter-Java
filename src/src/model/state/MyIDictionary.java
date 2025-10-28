package model.state;

public interface MyIDictionary<T1,T2> {
    void setValue(T1 key, T2 value);
    boolean isDefined(T1 key);
    T2 getValue(T1 key);
}
