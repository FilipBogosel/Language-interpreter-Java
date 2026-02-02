package view.GUI;

import controller.Controller;
import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import model.exception.DifferentTypesExpressionError;
import model.exception.VariableAlreadyDefined;
import model.exception.VariableNotDefinedError;
import model.state.ISymbolTable;
import model.state.MapTypeEnvironment;
import model.state.ProgramState;
import model.statement.IStatement;
import model.type.IType;
import repository.IRepository;
import repository.Repository;
import utils.Utils;

import java.io.IOException;
import java.util.List;


public class ProgramChooserController {
    @FXML
    private ListView<IStatement> programsListView;
    @FXML
    private Button runProgramButton;

    @FXML
    public void initialize() {
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        List<IStatement> programStatements = Utils.getProgramsToRun();
        ObservableList<IStatement> observableStatements = FXCollections.observableList(programStatements);
        programsListView.setItems(observableStatements);
    }
    /**
     * Method that is called when the user clicks on the "Run Program" button.
     */
    @FXML
    public void runSelectedProgram(ActionEvent event) {

        if (programsListView.getSelectionModel().getSelectedIndex() == -1) {
            Utils.showAlert("Error", "No program selected");
            return;
        }
        IStatement selectedStatement = programsListView.getSelectionModel().getSelectedItem();
        try {
            ISymbolTable<String, IType> typeEnvironment = new MapTypeEnvironment();
            selectedStatement.typecheck(typeEnvironment);
            ProgramState programState = Utils.createProgramState(selectedStatement);
            IRepository repository = new Repository("log_gui.txt");
            repository.addProgramState(programState);
            Controller backendController = new Controller(repository);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXML/ProgramExecutorWindow.fxml"));
            Parent root = loader.load();
            ProgramExecutorController executorController = loader.getController();
            executorController.setController(backendController);
            Stage stage = new Stage();
            stage.setTitle("Program Executor");
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(e -> backendController.shutDownExecutor());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Utils.showAlert("Program Executor Error", "Program Executor Error.");
        }
        catch (VariableAlreadyDefined | VariableNotDefinedError | DifferentTypesExpressionError e){
            Utils.showAlert("Typecheck Error", e.getMessage());
        }
    }

}
