package model.statement;

import model.exception.CloseNonOpenedFileError;
import model.exception.DifferentTypesExpressionError;
import model.expression.IExpression;
import model.state.ISymbolTable;
import model.state.ProgramState;
import model.type.IType;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;
import java.io.BufferedReader;


public record CloseReadFileStatement(IExpression expr) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {

        IValue val = expr.evaluate(state.symbolTable(), state.heapTable());
        if(!StringType.INSTANCE.equals(val.getType())) {
            throw new DifferentTypesExpressionError("Open read file statement must have as parameter a String value as result of innerExpression");
        }
        StringValue strVal = (StringValue) val;
        if(!state.fileTable().isDefined(strVal.value())){
            throw new CloseNonOpenedFileError("File not opened: " + val);
        }
        try{
            BufferedReader br = state.fileTable().getFilePointer(strVal.value());
            br.close();
            state.fileTable().closeFile(strVal.value());
        }
        catch(Exception e){
            throw new CloseNonOpenedFileError("File not opened: " + val);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseReadFileStatement(expr.deepCopy());
    }

    @Override
    public ISymbolTable<String, IType> typecheck(ISymbolTable<String, IType> typeEnvironment) {
        IType expType = expr.typecheck(typeEnvironment);
        if (expType.equals(StringType.INSTANCE)) {
            return typeEnvironment;
        }
        else {
            throw new DifferentTypesExpressionError("CloseReadFile stmt: innerExpression is not a string");
        }
    }

    @Override
    public String toString(){
        return "close file(" + expr.toString() + ")";
    }
}
