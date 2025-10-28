package model.type;

import model.value.BooleanValue;
import model.value.IValue;

public class BooleanType implements IType {

    public static final BooleanType INSTANCE = new BooleanType();
    private BooleanType(){}
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BooleanType;
    }
    @Override
    public String toString() {
        return "boolean";
    }

    @Override
    public IValue getDefaultValue() {
        return new BooleanValue(false);
    }

}
