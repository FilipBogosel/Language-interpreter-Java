package controller;

import model.state.ISymbolTable;
import model.state.MyIStack;
import model.state.ProgramState;
import model.statement.IStatement;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;

import java.io.IOException;
import java.sql.Ref;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements IController {
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
        //maybe display the program state here

        if (displayFlag) {
            this.repository.logProgramStateExecution();
        }
        while (!currentProgramState.executionStack().isEmpty()) {
            //also maybe display the program state here
            currentProgramState = oneStep(currentProgramState);
            if (displayFlag) {
                this.repository.logProgramStateExecution();
            }
            Map<Integer,IValue> heapContent = currentProgramState.heapTable().getContent();
            Collection<IValue> symbolTableValues = currentProgramState.symbolTable().getContent().values();

            Map<Integer,IValue> garbageCollectedHeap = garbageCollector(symbolTableValues, heapContent);

            currentProgramState.heapTable().setContent(garbageCollectedHeap);

            if (displayFlag) {
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

    @Override
    public List<Integer> getAddressFromValues(Collection<IValue> values) {
        return values.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.address();
                })
                .collect(Collectors.toList());
    }
    @Override
    public Map<Integer, IValue> garbageCollector(Collection<IValue> symTableValues, Map<Integer, IValue> heap) {

        List<Integer> symTableAddresses = getAddressFromValues(symTableValues);

        Set<Integer> reachableAddresses = new HashSet<>(symTableAddresses);

        boolean changed = true;
        while (changed) {
            // Get the values referred to by the reachable addresses
            List<IValue> currentReachableValues = heap.entrySet().stream()
                    .filter(e -> reachableAddresses.contains(e.getKey()))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());

            // Find the addresses of the values found above
            List<Integer> newAddresses = getAddressFromValues(currentReachableValues); // Use generic helper again

            // Add the new addresses to the reachable addresses
            changed = reachableAddresses.addAll(newAddresses);
        }

        // Filter the heap and return the map with only reachable addresses
        return heap.entrySet().stream()
                .filter(e -> reachableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
