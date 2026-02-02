package view.GUI;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty; // Import this!
import javafx.util.Pair;
import model.state.ProgramState;
import model.statement.IStatement;
import model.value.IValue;
import utils.Utils;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgramExecutorController {

    @FXML
    private TextField numberOfProgramStatesTextField; // Renamed to match FXML
    @FXML
    private TableView<Pair<Integer, IValue>> heapTableView;
    @FXML
    private TableColumn<Pair<Integer, IValue>, String> addressColumn;
    @FXML
    private TableColumn<Pair<Integer, IValue>, String> valueColumn;

    @FXML
    private ListView<String> outputListView;
    @FXML
    private ListView<String> fileTableListView;
    @FXML
    private ListView<Integer> programStateIdentifiersListView;

    @FXML
    private TableView<Pair<String, IValue>> symbolTableView;
    @FXML
    private TableColumn<Pair<String, IValue>, String> variableNameColumn;
    @FXML
    private TableColumn<Pair<String, IValue>, String> variableValueColumn;

    @FXML
    private ListView<String> executionStackListView;
    @FXML
    private Button runOneStepButton;

    private Controller controller;



    @FXML
    public void initialize() {
        addressColumn.setCellValueFactory(p
                -> new SimpleStringProperty(p.getValue().getKey().toString()));
        valueColumn.setCellValueFactory(p
                -> new SimpleStringProperty(p.getValue().getValue().toString()));

        variableNameColumn.setCellValueFactory(p
                -> new SimpleStringProperty(p.getValue().getKey()));
        variableValueColumn.setCellValueFactory(p
                -> new SimpleStringProperty(p.getValue().getValue().toString()));

        programStateIdentifiersListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                if (newVal != null) {
                    populateSymbolTable();
                    populateExecutionStack();
                }
        });
    }

    @FXML
    public void runOneStep(MouseEvent event) {
        if (controller == null) {
            Utils.showAlert("Error", "No program selected");
            return;
        }

        try {
            // 1. Get the list of current programs BEFORE the step
            List<ProgramState> programStates = controller.getAllProgramStates();

            if (!programStates.isEmpty()) {
                controller.oneStepForGUI();
                populate();
            } else {
                Utils.showAlert("Info", "Program finished");
            }

        } catch (Exception e) {
            Utils.showAlert("Error", e.getMessage());
            e.printStackTrace(); // Good for debugging
        }
    }


    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    private ProgramState getCurrentProgramState() {
        if (controller.getAllProgramStates().isEmpty()) {
            return null;
        }
        Integer selectedId = programStateIdentifiersListView.getSelectionModel().getSelectedItem();
        if (selectedId == null) {
            return controller.getAllProgramStates().getFirst();
        }
        for (ProgramState programState : controller.getAllProgramStates()) {
            if (programState.id() == selectedId) {
                return programState;
            }
        }
        return controller.getAllProgramStates().getFirst();
    }

    private void populate() {
        populateProgramStateIdentifiers();

        // Auto-select first program state
        if (!programStateIdentifiersListView.getItems().isEmpty()) {
            programStateIdentifiersListView.getSelectionModel().selectFirst();
        }

        populateHeap();
        populateFileTable();
        populateOutput();
        populateSymbolTable();
        populateExecutionStack();
    }


    private void populateSymbolTable()  {
        ProgramState program = getCurrentProgramState();
        if (program == null) return;
        List<Pair<String, IValue>> symbolTableEntries = new ArrayList<>();

        Map<String, IValue> symbols = program.symbolTable().getContent();
        for (Map.Entry<String, IValue> entry : symbols.entrySet()) {
            symbolTableEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }

        symbolTableView.setItems(FXCollections.observableArrayList(symbolTableEntries));
    }

    private void populateExecutionStack() {
        ProgramState program = getCurrentProgramState();
        if (program == null) {
            executionStackListView.getItems().clear();
            return;
        }

        List<String> stackList = new ArrayList<>();
        for (IStatement statement : program.executionStack().getContent().reversed()) {
            stackList.add(statement.toString());
        }

        executionStackListView.setItems(FXCollections.observableArrayList(stackList));
    }

    private void populateOutput() {
        ProgramState program = getCurrentProgramState();
        if (program == null) return;

        List<String> output = program.outputList().getContent().stream()
                .map(IValue::toString)
                .collect(Collectors.toList());

        outputListView.setItems(FXCollections.observableArrayList(output));
    }

    private void populateFileTable() {
        ProgramState program = getCurrentProgramState();
        if (program == null){
            return;
        }
        List<String> files = new ArrayList<>(program.fileTable().getContent().keySet());
        fileTableListView.setItems(FXCollections.observableArrayList(files));
    }

    private void populateProgramStateIdentifiers() {
        List<ProgramState> programStates = controller.getAllProgramStates();
        numberOfProgramStatesTextField.setText(String.valueOf(programStates.size()));

        List<Integer> idList = programStates.stream()
                .map(ProgramState::id)
                .collect(Collectors.toList());

        programStateIdentifiersListView.setItems(FXCollections.observableArrayList(idList));
        programStateIdentifiersListView.refresh();
    }

    private void populateHeap() {
        ProgramState programState = getCurrentProgramState();
        if (programState == null) {
            return;
        }
        Map<Integer, IValue> heapContent = programState.heapTable().getContent();
        List<Pair<Integer, IValue>> heapEntries = new ArrayList<>();
        for (Map.Entry<Integer, IValue> entry : heapContent.entrySet()) {
            heapEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        heapTableView.setItems(FXCollections.observableList(heapEntries));
    }
}