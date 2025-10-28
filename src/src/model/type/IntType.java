package model.type;


import model.value.IValue;
import model.value.IntValue;

public class IntType implements IType {

    public static final IntType INSTANCE = new IntType();
    private IntType(){}
    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntType;
    }
    @Override
    public String toString() {return "int";}

    @Override
    public IValue getDefaultValue() {
        return new IntValue(0);
    }
}
