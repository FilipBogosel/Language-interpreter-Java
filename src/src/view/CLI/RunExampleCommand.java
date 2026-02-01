package view.CLI;

import controller.IController;


public class RunExampleCommand extends Command {
    private final IController controller;

    public RunExampleCommand(String key, String description, IController controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Executing: " + this.getDescription());
            controller.allSteps();
            System.out.println("Execution finished.");
        } catch (Exception e) {
            System.err.println(e.getClass());
            System.err.println(e.getMessage());
        }
    }
}