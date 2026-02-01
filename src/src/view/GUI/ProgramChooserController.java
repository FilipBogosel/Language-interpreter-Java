package view.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import model.statement.IStatement;
import utils.Utils;

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
        IStatement selectedProgram = programsListView.getSelectionModel().getSelectedItem();
        if (selectedProgram == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("A program has to be selected");
            alert.showAndWait();
        }
        else {
            //TODO: we will do this later passing this to window 2
            System.out.println("Selected program is " + selectedProgram);
        }
    }
}
