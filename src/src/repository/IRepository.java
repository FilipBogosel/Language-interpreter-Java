package repository;

import model.state.ProgramState;

import java.util.List;

public interface IRepository {
    public void addProgramState(ProgramState programState);
    public ProgramState getCurrentProgramState();
    public List<ProgramState> getAllProgramStates();
}
