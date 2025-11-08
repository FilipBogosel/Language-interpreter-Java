package model.expression;


import model.exception.DifferentTypesExpressionError;
import model.exception.DivisionByZeroError;
import model.exception.InvalidOperationError;
import model.state.ISymbolTable;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

public record ArithmeticExpression(IExpression leftExpression, IExpression rightExpression, int operation) implements  IExpression{

    @Override
    public IValue evaluate(ISymbolTable<String,IValue> symbolTable) throws DifferentTypesExpressionError {
        IValue leftValue = leftExpression.evaluate(symbolTable);
        IValue rightValue = rightExpression.evaluate(symbolTable);
        if(!(leftValue.getType() instanceof IntType) || !(rightValue.getType() instanceof IntType)){
            throw new DifferentTypesExpressionError("Needed integer type for arithmetic expression");
        }
        if(!leftValue.getType().equals(rightValue.getType())){
            throw new DifferentTypesExpressionError("Type mismatch in ArithmeticExpression");
        }
        IntValue leftIntValue = (IntValue)leftValue;
        IntValue rightIntValue = (IntValue)rightValue;
        if(operation == 1){
            int resultValue = leftIntValue.value() + rightIntValue.value();
            return new IntValue(resultValue);
        }
        else if(operation == 2){
            int resultValue = leftIntValue.value() - rightIntValue.value();
            return new IntValue(resultValue);
        }
        else if(operation == 3){
            int resultValue = leftIntValue.value() * rightIntValue.value();
            return new IntValue(resultValue);
        }
        else if(operation == 4){
            if(rightIntValue.value() == 0){
                throw new DivisionByZeroError("Zero provided as denominator");
            }
            int resultValue = leftIntValue.value() / rightIntValue.value();
            return new IntValue(resultValue);
        }
        else{
            throw new InvalidOperationError("Invalid operation for ArithmeticExpression");
        }

    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(leftExpression.deepCopy(), rightExpression.deepCopy(), operation);
    }

    @Override
    public String toString() {
        String op;
        if(operation == 1){
            op = "+";
        }
        else if(operation == 2){
            op = "-";
        }
        else if(operation == 3){
            op = "*";
        }
        else if(operation == 4){
            op = "/";
        }
        else{
            op = "??";
        }
        return "(" + leftExpression + " " + op + " " + rightExpression + ")";
    }
}
