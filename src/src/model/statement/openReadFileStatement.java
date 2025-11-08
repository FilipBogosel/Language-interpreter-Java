package model.statement;

import model.exception.DifferentTypesExpressionError;
import model.exception.FileAlreadyOpenedError;
import model.exception.OpenFileError;
import model.expression.IExpression;
import model.state.MapFileTable;
import model.state.MapSymbolTable;
import model.state.ProgramState;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public record openReadFileStatement(IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        MapFileTable fileTable = state.fileTable();
        IValue val = expression.evaluate(state.symbolTable());
        if(!StringType.INSTANCE.equals(val.getType())) {
            throw new DifferentTypesExpressionError("Open read file statement must have as parameter a String value as result of expression");
        }
        StringValue strVal = (StringValue) val;
        if(fileTable.isDefined(strVal.value())){
            throw new FileAlreadyOpenedError("File already opened: " + val);
        }

        //open the file and add it to the file table
        try{
            BufferedReader reader = new BufferedReader(new FileReader(strVal.value()));
            fileTable.openFile(strVal.value(), reader);
        }
        catch(Exception e){
            throw new OpenFileError("Error opening file: " + val);
        }

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new openReadFileStatement(expression.deepCopy());
    }

    @Override
    public String toString(){
        return "opened file(" + expression.toString() + ")";
    }
}
