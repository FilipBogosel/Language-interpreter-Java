package view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class MainGUI extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXML/ProgramChooserWindow.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,1200,750);
        primaryStage.setTitle("Program Chooser");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
