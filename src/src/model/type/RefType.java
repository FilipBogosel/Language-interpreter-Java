package model.type;

import model.value.IValue;
import model.value.RefValue;

public record RefType(IType innerType) implements IType{
    @Override
    public IValue getDefaultValue() {
        return new RefValue(0,innerType);
    }
    @Override
    public String toString() { return "Ref(" +innerType.toString()+")";}
    @Override
    public boolean equals(Object another){
        if (another instanceof RefType)
            return innerType.equals(((RefType) another).innerType());
        else
            return false;
    }
}
