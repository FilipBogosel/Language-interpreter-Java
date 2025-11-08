package controller;

import model.state.MyIStack;
import model.state.ProgramState;
import model.statement.IStatement;
import repository.IRepository;

import java.io.IOException;
import java.util.List;

public class Controller implements IController{
    IRepository repository;
    boolean displayFlag;
    public Controller(IRepository repository) {
        this.repository = repository;
        this.displayFlag = false;
    }

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
    public void allSteps() throws IOException {
        ProgramState currentProgramState = getCurrentProgramState();
        //maybe display program state here

        if(displayFlag){
            this.repository.logProgramStateExecution();
        }
        int stepCount = 0;
        while(!currentProgramState.executionStack().isEmpty()){
            //also maybe display the program state here
            currentProgramState = oneStep(currentProgramState);
            if(displayFlag){
                this.repository.logProgramStateExecution();
            }
        }
    }

    @Override
    public void displayCurrentProgramState() {
        IO.println(this.getCurrentProgramState());
    }

    @Override
    public void toggleDisplayFlag() {
        this.displayFlag = !this.displayFlag;
    }
}
