package view;

import controller.Controller;
import model.state.*;
import model.statement.IStatement;
import repository.IRepository;
import repository.Repository;

import java.util.List;
import java.util.Scanner;

public class View {
    private final List<IStatement> examples;

    public View(List<IStatement> examples) {
        this.examples = examples;
    }

    private void printMenu() {
        System.out.println("\n--- Toy Language Interpreter Menu ---");
        System.out.println("0. Exit");
        for (int i = 0; i < examples.size(); i++) {
            // We use i+1 for a 1-based menu
            System.out.println((i + 1) + ". Run example " + (i + 1));
        }
    }

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("> ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice == 0) {
                    System.out.println("Exiting.");
                    return; // Exit the loop
                }

                if (choice > 0 && choice <= examples.size()) {
                    // Get the selected program (adjust for 0-based list index)
                    IStatement selectedProgram = examples.get(choice - 1);
                    runProgram(selectedProgram);
                } else {
                    System.err.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void runProgram(IStatement program) {

        // 1. Create the data structures
        MyIStack<IStatement> exeStack = new ListExecutionStack<>();
        ISymbolTable<String, model.value.IValue> symTable = new MapSymbolTable();
        MyIList<model.value.IValue> outputList = new ListOutput<>();

        // 2. Create the ProgramState
        ProgramState programState = new ProgramState(symTable, exeStack, outputList, program);


        IRepository repository = new Repository();
        Controller controller = new Controller(repository);

        controller.addProgramState(programState);
        controller.toggleDisplayFlag();

        // 5. Run the program
        System.out.println("Executing program...");
        controller.allSteps();// This will run the program to completion
        System.out.println("...Execution finished.");
    }
}
