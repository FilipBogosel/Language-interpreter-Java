package model.value;

import model.type.IType;
import model.type.RefType;

public record RefValue(int address, IType locationType) implements IValue{
    @Override
    public String toString() {
        return "Ref(" + address + "," + locationType.toString() + ")";
    }
    @Override
    public IType getType() {
        return new RefType(locationType);
    }
}
