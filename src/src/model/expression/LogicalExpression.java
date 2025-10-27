package model.expression;

import model.exception.DifferentTypesExpressionError;
import model.exception.InvalidOperationError;
import model.state.ISymbolTable;
import model.type.BooleanType;
import model.value.BooleanValue;
import model.value.IValue;


public record LogicalExpression(IExpression leftExpression, IExpression rightExpression, int operation) implements IExpression{
    @Override
    public IValue evaluate(ISymbolTable symbolTable) {
        BooleanValue leftValue = (BooleanValue) leftExpression.evaluate(symbolTable);
        BooleanValue rightValue = (BooleanValue) rightExpression.evaluate(symbolTable);
        if(!(leftValue.getType() instanceof BooleanType) || !(rightValue.getType() instanceof BooleanType)){
            throw new DifferentTypesExpressionError("Needed boolean type for logical expression");
        }
        if(!leftValue.getType().equals(rightValue.getType())){
            throw new DifferentTypesExpressionError("Type mismatch in LogicalExpression");
        }
        if(operation == 1){
            boolean resultValue = leftValue.value() && rightValue.value();
            return new BooleanValue(resultValue);
        }
        else if(operation == 2){
            boolean resultValue = leftValue.value() || rightValue.value();
            return new BooleanValue(resultValue);
        }
        else{
            throw new InvalidOperationError("Invalid operation for LogicalExpression");
        }
    }
}

