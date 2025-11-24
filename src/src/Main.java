
import model.expression.*;
import model.state.*;
import model.statement.*;
import model.type.BooleanType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BooleanValue;
import model.value.IntValue;
import model.value.StringValue;
import controller.*;
import repository.*;
import java.util.ArrayList;
import java.util.List;
import view.*;

public class Main {

     static void main() {

        // Get all hardcoded example statements
        List<IStatement> allExamples = getHardcodedExamples();

        // Create the menu
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));

        // Create a dedicated Controller and Repository for EACH example
        int exampleNumber = 1;
        for (IStatement statement : allExamples) {
            try {
                ProgramState programState = createProgramState(statement);

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
            }
            exampleNumber++;
        }

        menu.show();
    }

    private static ProgramState createProgramState(IStatement statement) {
        // Create fresh ADTs for the new ProgramState
        MyIStack<IStatement> executionStack = new ListExecutionStack<>();
        ISymbolTable<String, model.value.IValue> symbolTable = new MapSymbolTable();
        MyIList<model.value.IValue> outputList = new ListOutput<>();
        IFileTable fileTable = new MapFileTable();
        IHeapTable heapTable = new MapHeapTable();

        return new ProgramState(symbolTable, executionStack, outputList,fileTable, heapTable, statement);
    }

    /**
     * Creates a List of all hardcoded example IStatements from the doc
     */
    private static List<IStatement> getHardcodedExamples() {
        List<IStatement> examples = new ArrayList<>();

        // Example 1: int v; v=2; Print(v)
        IStatement ex1 = new CompoundStatement(
                new VariableDeclarationStatement(IntType.INSTANCE, "v"),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
        examples.add(ex1);


        // Example 2: int a; int b; a=2+3*5; b=a+1; Print(b)
        IStatement ex2 = new CompoundStatement(
                new VariableDeclarationStatement(IntType.INSTANCE, "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(IntType.INSTANCE, "b"),
                        new CompoundStatement(
                                new AssignmentStatement("a",
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntValue(2)),
                                                new ArithmeticExpression(
                                                        new ValueExpression(new IntValue(3)),
                                                        new ValueExpression(new IntValue(5)),
                                                        3 // 3 = '*'
                                                ),
                                                1 // 1 = '+'
                                        )
                                ),
                                new CompoundStatement(
                                        new AssignmentStatement("b",
                                                new ArithmeticExpression(
                                                        new VariableExpression("a"),
                                                        new ValueExpression(new IntValue(1)),
                                                        1 // 1 = '+'
                                                )
                                        ),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
        examples.add(ex2);


        // Example 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        IStatement ex3 = new CompoundStatement(
                new VariableDeclarationStatement(BooleanType.INSTANCE, "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(IntType.INSTANCE, "v"),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new BooleanValue(true))),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );
        examples.add(ex3);

        // Example 4: string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
        IStatement ex4 = new CompoundStatement(
                new VariableDeclarationStatement(StringType.INSTANCE, "varf"),
                new CompoundStatement(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(IntType.INSTANCE, "varc"),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFileStatement(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        examples.add(ex4);

        // Example 5: Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)
        IStatement ex5 = new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v"),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new RefType(new RefType(IntType.INSTANCE)), "a"),
                                new CompoundStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        );
        examples.add(ex5);

        // Example 6: Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)
        IStatement ex6 = new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v"),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new RefType(new RefType(IntType.INSTANCE)), "a"),
                                new CompoundStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new PrintStatement(
                                                        new ArithmeticExpression(
                                                                new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                                                new ValueExpression(new IntValue(5)),
                                                                1 // 1 = '+'
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        examples.add(ex6);

        // Example 7: Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
        IStatement ex7 = new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v"),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new HeapWriteStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(
                                                new ArithmeticExpression(
                                                        new ReadHeapExpression(new VariableExpression("v")),
                                                        new ValueExpression(new IntValue(5)),
                                                        1 // 1 = '+'
                                                )
                                        )
                                )
                        )
                )
        );
        examples.add(ex7);

        // Example 8: Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
        IStatement ex8 = new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v"),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new RefType(new RefType(IntType.INSTANCE)), "a"),
                                new CompoundStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );
        examples.add(ex8);

        // Example 9: int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement ex9 = new CompoundStatement(
                new VariableDeclarationStatement(IntType.INSTANCE, "v"),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), 5), // 5 = '>'
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement("v",
                                                        new ArithmeticExpression(
                                                                new VariableExpression("v"),
                                                                new ValueExpression(new IntValue(1)),
                                                                2 // 2 = '-'
                                                        )
                                                )
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );
        examples.add(ex9);

        // Return the list of all examples
        return examples;
    }
}
