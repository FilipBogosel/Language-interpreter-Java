package view.CLI;

import model.state.*;
import model.statement.*;
import model.type.*;
import controller.*;
import repository.*;

import java.util.List;

import utils.Utils;

public class Main {

     static void main() {

        // Get all hardcoded example statements
        List<IStatement> allExamples = Utils.getProgramsToRun();

        // Create the menu
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));

        // Create a dedicated Controller and Repository for EACH example
        int exampleNumber = 1;
        for (IStatement statement : allExamples) {
            try {

                ISymbolTable<String, IType> typeEnvironment = new MapTypeEnvironment();
                statement.typecheck(typeEnvironment);
                ProgramState programState = Utils.createProgramState(statement);

                String logFilePath = "log" + exampleNumber + ".txt";
                IRepository repository = new Repository(logFilePath);
                repository.addProgramState(programState);

                IController controller = new Controller(repository);
                controller.toggleDisplayFlag();

                menu.addCommand(new RunExampleCommand(
                        String.valueOf(exampleNumber),
                        statement.toString(),
                        controller
                ));
            } catch (Exception e) {
                System.err.println("Error initializing example: " + statement.toString());
                System.err.println(e.getMessage());
                System.exit(1);
            }
            exampleNumber++;
        }

        menu.show();
    }



}
