package controller;

import model.state.ProgramState;
import model.statement.IStatement;

import java.util.List;

public interface IController {
    public void addProgramState(ProgramState programState);
    public ProgramState getCurrentProgramState();
    public List<ProgramState> getAllProgramStates();
    ProgramState oneStep(ProgramState programState);
    void allSteps();
    //this will be replaced later with writing in a text file
    void displayCurrentProgramState();
    void toggleDisplayFlag();
}
