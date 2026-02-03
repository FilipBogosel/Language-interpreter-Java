package model.statement;

import model.exception.DifferentTypesExpressionError;
import model.exception.FileNotOpenedError;
import model.exception.ReadError;
import model.expression.IExpression;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

public record ReadFileStatement(IExpression expression, String variableName) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state)  {
        IValue val = expression.evaluate(state.symbolTable(), state.heapTable());
        if(!StringType.INSTANCE.equals(val.getType())) {
            throw new DifferentTypesExpressionError("Read file statement must have as parameter a String value as result of innerExpression");
        }
        StringValue strVal = (StringValue) val;
        if(!state.fileTable().isDefined(strVal.value())){
            throw new FileNotOpenedError("File not opened: " + val);
        }
        try{
            String line = state.fileTable().getFilePointer(strVal.value()).readLine();
            IntValue intVal;
            if(line == null){
                intVal = new IntValue(0);
            }
            else {
                intVal = new IntValue(Integer.parseInt(line));
            }
            state.symbolTable().setValue(variableName, intVal);
        }
        catch(Exception e){
            throw new ReadError("Error reading from file: " + val);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), variableName);
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        IType expType = expression.typecheck(typeEnvironment);
        if (!expType.equals(StringType.INSTANCE)) {
            throw new DifferentTypesExpressionError("ReadFile stmt: innerExpression is not a string");
        }
        IType varType = typeEnvironment.getType(variableName);
        if (!varType.equals(IntType.INSTANCE)) {
            throw new DifferentTypesExpressionError("ReadFile stmt: variable is not an integer");
        }
        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "read file(" + expression.toString() + ", " + variableName + ")";
    }
}
