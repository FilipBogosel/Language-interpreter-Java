package controller;

import model.state.IHeapTable;
import model.state.ProgramState;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController {
    IRepository repository;
    boolean displayFlag;
    ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
        this.displayFlag = false;
    }

    @Override
    public void addProgramState(ProgramState programState) {
        repository.addProgramState(programState);
    }

    @Override
    public List<ProgramState> getAllProgramStates() {
        return repository.getAllProgramStates();
    }


    @Override
    public void allSteps() throws IOException {
        this.executor = Executors.newFixedThreadPool(5);
        IHeapTable commonHeapTable = getAllProgramStates().getFirst().heapTable();
        List<ProgramState> runningProgramStates = removeCompletedProgramStates(getAllProgramStates());
        while(!runningProgramStates.isEmpty()){
            Map<Integer,IValue> garbageCollectedHeap = garbageCollector(this.getAllSymbolTableValues(), commonHeapTable.getContent());
            commonHeapTable.setContent(garbageCollectedHeap);//common heap table -> if updated, all the program
            // states will be updated(it's a reference)
            oneStepForAllPrograms(runningProgramStates);
            runningProgramStates = removeCompletedProgramStates(getAllProgramStates());
        }
        executor.shutdownNow();
        repository.setProgramStates(getAllProgramStates());
    }


    @Override
    public void toggleDisplayFlag() {
        this.displayFlag = !this.displayFlag;
    }

    @Override
    public Set<Integer> getAddressFromValues(Collection<IValue> values) {
        return values.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.address();
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Map<Integer, IValue> garbageCollector(Collection<IValue> symTableValues, Map<Integer, IValue> heap) {

        Set<Integer> reachableAddresses = getAddressFromValues(symTableValues);

        boolean changed = true;
        while (changed) {
            // Get the values referred to by the reachable addresses
            List<IValue> currentReachableValues = heap.entrySet().stream()
                    .filter(e -> reachableAddresses.contains(e.getKey()))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());

            // Find the addresses of the values found above
            Set<Integer> newAddresses = getAddressFromValues(currentReachableValues); // Use generic helper again

            // Add the new addresses to the reachable addresses
            changed = reachableAddresses.addAll(newAddresses);
        }

        // Filter the heap and return the map with only reachable addresses
        return heap.entrySet().stream()
                .filter(e -> reachableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public List<ProgramState> removeCompletedProgramStates(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    @Override
    public void oneStepForAllPrograms(List<ProgramState> programStates) {
        programStates.forEach(programState -> {
            try {
                repository.logProgramStateExecution(programState);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        //Make the list of Callable items functionally
        List<Callable<ProgramState>> callableList = programStates.stream()
                    .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                    .collect(Collectors.toList());

        //start the execution of the callables and functionally retrieve in a list
        //the newly created program states(by forks)
        try {
            List<ProgramState> newProgramSates = executor.invokeAll(callableList).stream()
                        .map(future -> {
                            try {
                                return future.get();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            programStates.addAll(newProgramSates);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        programStates.forEach(programState -> {
            try {
                repository.logProgramStateExecution(programState);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        repository.setProgramStates(programStates);

    }

    /**
     * @return List of all the values stored in the symbol table of all the program states.
     */
    @Override
    public List<IValue> getAllSymbolTableValues() {
        return getAllProgramStates().stream()
                .flatMap(programState -> programState.symbolTable().getContent().values().stream())
                .collect(Collectors.toList());
    }

    @Override
    public void oneStepForGUI() {
        // Create the executor specifically for this step
        executor = Executors.newFixedThreadPool(2);

        // Get all program states (including completed ones for now)
        List<ProgramState> allProgramStates = repository.getAllProgramStates();

        // Filter to get only active programs for execution
        List<ProgramState> activeProgramStates = removeCompletedProgramStates(allProgramStates);

        if (!activeProgramStates.isEmpty()) {
            IHeapTable commonHeapTable = activeProgramStates.getFirst().heapTable();
            Map<Integer, IValue> garbageCollectedHeap = garbageCollector(
                    getAllSymbolTableValues(),
                    commonHeapTable.getContent()
            );

            commonHeapTable.setContent(garbageCollectedHeap);

            oneStepForAllPrograms(activeProgramStates);

            // After execution, update the repository with all states (including newly completed ones)
            // But keep at least one state for UI access to shared structures
            List<ProgramState> updatedStates = repository.getAllProgramStates();
            List<ProgramState> remainingActive = removeCompletedProgramStates(updatedStates);

            // If all states completed, keep the first completed state for UI access
            if (remainingActive.isEmpty() && !updatedStates.isEmpty()) {
                repository.setProgramStates(List.of(updatedStates.getFirst()));
            } else {
                repository.setProgramStates(remainingActive);
            }
        }

        // Shutdown the executor so we don't leak threads
        executor.shutdownNow();
    }

    @Override
    public void shutDownExecutor() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
    }

}
