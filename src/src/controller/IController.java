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
import java.util.Set;

public interface IController {
    void addProgramState(ProgramState programState);
    List<ProgramState> getAllProgramStates();
    void allSteps() throws IOException;
    void toggleDisplayFlag();
    Set<Integer> getAddressFromValues(Collection<IValue> symbolTableValues);
    Map<Integer,IValue> garbageCollector(Collection<IValue> symbolTableValues, Map<Integer, IValue> heap);
    List<ProgramState> removeCompletedProgramStates(List<ProgramState> programStates);
    void oneStepForAllPrograms(List<ProgramState> programStates);
    List<IValue> getAllSymbolTableValues();

}
