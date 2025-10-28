package model.value;

import model.type.BooleanType;
import model.type.IType;

public record BooleanValue(boolean value) implements IValue {
    @Override
    public IType getType() {
        return BooleanType.INSTANCE;
    }

    public String toString() {
        return this.value ? "true" : "false";
    }
}
