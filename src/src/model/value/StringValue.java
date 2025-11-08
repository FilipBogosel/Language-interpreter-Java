package model.value;

import model.type.IType;
import model.type.StringType;

public record StringValue(String value) implements IValue {

    @Override
    public IType getType() {
        return StringType.INSTANCE;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
