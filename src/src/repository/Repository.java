package repository;

import model.state.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private final List<ProgramState>  programStates = new ArrayList<>();
    private final String filename;

    public Repository(String filename) {
        this.filename = filename;
    }

    @Override
    public void addProgramState(ProgramState programState) {
        programStates.add(programState);
    }
    @Override
    public ProgramState getCurrentProgramState() {
        if(programStates.isEmpty()){
            return null;
        }
        return programStates.getLast();
    }

    @Override
    public List<ProgramState> getAllProgramStates() {
        return programStates;
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws IOException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.filename,true)));){
            writer.println(programState.toString());
        }
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates.clear();
        this.programStates.addAll(programStates);
    }
}
