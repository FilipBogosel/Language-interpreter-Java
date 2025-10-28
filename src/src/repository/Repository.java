package repository;

import model.state.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private final List<ProgramState>  programStates = new ArrayList<>();
    @Override
    public void addProgramState(ProgramState programState) {
        programStates.add(programState);
    }
    @Override
    public ProgramState getCurrentProgramState() {
        return programStates.getLast();
    }

    @Override
    public List<ProgramState> getAllProgramStates() {
        return programStates;
    }
}
