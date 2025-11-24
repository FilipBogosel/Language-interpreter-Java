package controller;

import model.state.ISymbolTable;
import model.state.ProgramState;
import model.statement.IStatement;
import model.value.IValue;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IController {
    public void addProgramState(ProgramState programState);
    public ProgramState getCurrentProgramState();
    public List<ProgramState> getAllProgramStates();
    ProgramState oneStep(ProgramState programState);
    void allSteps() throws IOException;
    void displayCurrentProgramState();
    void toggleDisplayFlag();
    List<Integer> getAddressFromValues(Collection<IValue> symbolTableValues);
    Map<Integer,IValue> garbageCollector(Collection<IValue> symbolTableValues, Map<Integer, IValue> heap);


}
