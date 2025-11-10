package model.expression;

import model.exception.DifferentTypesExpressionError;
import model.exception.InvalidOperationError;
import model.state.ISymbolTable;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;
import model.value.BooleanValue;

public record RelationalExpression(IExpression leftExpression, IExpression rightExpression,
                                   int operator) implements IExpression {

    @Override
    public IValue evaluate(ISymbolTable<String, IValue> symbolTable) {

        IValue leftValue = leftExpression.evaluate(symbolTable);
        IValue rightValue = rightExpression.evaluate(symbolTable);
        if (!(leftValue.getType() instanceof IntType) || !(rightValue.getType() instanceof IntType)) {
            throw new DifferentTypesExpressionError("Needed integer type for relational expression");
        }
        if (!leftValue.getType().equals(rightValue.getType())) {
            throw new DifferentTypesExpressionError("Type mismatch in RelationalExpression");
        }
        IntValue leftIntValue = (IntValue) leftValue;
        IntValue rightIntValue = (IntValue) rightValue;
        return switch (operator) {
            case 1 -> new BooleanValue(leftIntValue.value() < rightIntValue.value());
            case 2 -> new BooleanValue(leftIntValue.value() <= rightIntValue.value());
            case 3 -> new BooleanValue(leftIntValue.value() == rightIntValue.value());
            case 4 -> new BooleanValue(leftIntValue.value() != rightIntValue.value());
            case 5 -> new BooleanValue(leftIntValue.value() > rightIntValue.value());
            case 6 -> new BooleanValue(leftIntValue.value() >= rightIntValue.value());
            default -> throw new InvalidOperationError("Invalid operation for RelationalExpression");
        };
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(leftExpression.deepCopy(), rightExpression.deepCopy(), operator);
    }

    @Override
    public String toString() {
        String op;
        return switch (operator) {
            case 1 -> leftExpression.toString() + " < " + rightExpression.toString();
            case 2 -> leftExpression.toString() + " <= " + rightExpression.toString();
            case 3 -> leftExpression.toString() + " == " + rightExpression.toString();
            case 4 -> leftExpression.toString() + " != " + rightExpression.toString();
            case 5 -> leftExpression.toString() + " > " + rightExpression.toString();
            case 6 -> leftExpression.toString() + " >= " + rightExpression.toString();
            default -> "";
        };
    }
}
