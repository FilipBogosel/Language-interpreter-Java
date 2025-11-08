package model.value;

import model.type.IType;
import model.type.IntType;

public record IntValue(int value) implements IValue {

    @Override
    public IType getType() {
        return IntType.INSTANCE;
    }
    @Override
    public String toString() {
        return ("" + value);
    }


}
