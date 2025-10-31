
import model.expression.*;
import model.state.*;
import model.statement.*;
import model.type.BooleanType;
import model.type.IntType;
import model.value.BooleanValue;
import model.value.IntValue;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class Main {

     static void main() {
        // Build the list of hardcoded example statements
        List<IStatement> examples = getHardcodedExamples();

        // The View will manage the menu and program selection
        View view = new View(examples);
        view.runMenu();
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
                                                1
                                        )
                                ),
                                new CompoundStatement(
                                        new AssignmentStatement("b",
                                                new ArithmeticExpression(
                                                        new VariableExpression("a"),
                                                        new ValueExpression(new IntValue(1)),
                                                        1
                                                )
                                        ),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
        examples.add(ex2);


        // Example 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        // From [cite: 395-398]
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

        // Return the list of all examples
        return examples;
    }
}
