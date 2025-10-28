package controller;

import model.state.MyIStack;
import model.state.ProgramState;
import model.statement.IStatement;
import repository.IRepository;

import java.util.List;

public class Controller implements IController{
    IRepository repository;

    @Override
    public void addProgramState(ProgramState programState) {
        repository.addProgramState(programState);
    }

    @Override
    public ProgramState getCurrentProgramState() {
        return repository.getCurrentProgramState();
    }

    @Override
    public List<ProgramState> getAllProgramStates() {
        return repository.getAllProgramStates();
    }

    @Override
    public ProgramState oneStep(ProgramState programState) {
        MyIStack<IStatement> executionStack = programState.executionStack();
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(programState);
    }

    @Override
    public void allSteps() {
        ProgramState currentProgramState = getCurrentProgramState();
        //maybe display program state here
        while(!currentProgramState.executionStack().isEmpty()){
            ProgramState nextProgramState = oneStep(currentProgramState);
            //also maybe display the program state here
        }
    }

    @Override
    public void displayCurrentProgramState() {
        //
    }
}
