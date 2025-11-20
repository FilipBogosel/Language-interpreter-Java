package model.expression;

import model.exception.DifferentTypesExpressionError;
import model.exception.InvalidOperationError;
import model.state.IHeapTable;
import model.state.ISymbolTable;
import model.type.BooleanType;
import model.value.BooleanValue;
import model.value.IValue;


public record LogicalExpression(IExpression leftExpression, IExpression rightExpression, int operation) implements IExpression{
    @Override
    public IValue evaluate(ISymbolTable<String,IValue> symbolTable, IHeapTable heapTable) {
        IValue leftValue = leftExpression.evaluate(symbolTable, heapTable);
        IValue rightValue = rightExpression.evaluate(symbolTable, heapTable);
        if(!(leftValue.getType() instanceof BooleanType) || !(rightValue.getType() instanceof BooleanType)){
            throw new DifferentTypesExpressionError("Needed boolean type for logical expression");
        }
        if(!leftValue.getType().equals(rightValue.getType())){
            throw new DifferentTypesExpressionError("Type mismatch in LogicalExpression");
        }
        BooleanValue BoolLeftValue = (BooleanValue) leftValue;
        BooleanValue BoolRightValue = (BooleanValue) rightValue;
        if(operation == 1){
            boolean resultValue = BoolLeftValue.value() && BoolRightValue.value();
            return new BooleanValue(resultValue);
        }
        else if(operation == 2){
            boolean resultValue = BoolLeftValue.value() || BoolRightValue.value();
            return new BooleanValue(resultValue);
        }
        else{
            throw new InvalidOperationError("Invalid operation for LogicalExpression");
        }
    }

    @Override
    public IExpression deepCopy() {
        return new LogicalExpression(leftExpression.deepCopy(), rightExpression.deepCopy(), operation);
    }

    @Override
    public String toString() {
        String op;
        if(operation == 1){
            op = "&&";
        }
        else if(operation == 2){
            op = "||";
        }
        else{
            op = "??";
        }
        return "(" + leftExpression + " " + op + " " + rightExpression + ")";
    }
}

