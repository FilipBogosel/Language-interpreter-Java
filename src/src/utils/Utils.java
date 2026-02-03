package utils;

import javafx.scene.control.Alert;
import model.expression.*;
import model.state.*;
import model.statement.*;
import model.type.BooleanType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BooleanValue;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<IStatement> getProgramsToRun() {
        List<IStatement> examples = new ArrayList<>();

        examples.add(getExample1());
        examples.add(getExample2());
        examples.add(getExample3());
        examples.add(getExample4());
        examples.add(getExample5());
        examples.add(getExample6());
        examples.add(getExample7());
        examples.add(getExample8());
        examples.add(getExample9());
        examples.add(getExample10());
        examples.add(getExample11());
        examples.add(getExample12());
        examples.add(getSwitchExample());
        examples.add(getSemaphoreExample());
        examples.add(getForExample());
        examples.add(getLockExample());
        examples.add(getRepeatUntilExample());
        examples.add(getCyclicBarrierExample());
        examples.add(getConditionalAssignmentExample());
        examples.add(getCountDownLatchExample());

        return examples;
    }

    // Example 1: int v; v=2; Print(v)
    private static IStatement getExample1() {
        return new CompoundStatement(
                new VariableDeclarationStatement(IntType.INSTANCE, "v"),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
    }

    // Example 2: int a; int b; a=2+3*5; b=a+1; Print(b)
    private static IStatement getExample2() {
        return new CompoundStatement(
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
    }

    // Example 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
    private static IStatement getExample3() {
        return new CompoundStatement(
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
    }

    // Example 4: string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
    private static IStatement getExample4() {
        return new CompoundStatement(
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
    }

    // Example 5: Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)
    private static IStatement getExample5() {
        return new CompoundStatement(
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
    }

    // Example 6: Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)
    private static IStatement getExample6() {
        return new CompoundStatement(
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
    }

    // Example 7: Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5);
    private static IStatement getExample7() {
        return new CompoundStatement(
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
    }

    // Example 8: Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
    private static IStatement getExample8() {
        return new CompoundStatement(
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
    }

    // Example 9: int v; v=4; (while (v>0) print(v);v=v-1);print(v)
    private static IStatement getExample9() {
        return new CompoundStatement(
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
    }

    // Example 10: Ref int v; new(v,20); new(v,30); print(rH(v))
    private static IStatement getExample10() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v"),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v")))
                        )
                )
        );
    }

    // Example 11: Concurrent execution with fork
    // Ref int a; int v; new(a,22); fork(...); print(v); print(rH(a))
    private static IStatement getExample11() {
        CompoundStatement statementSecond = new CompoundStatement(
                new PrintStatement(new VariableExpression("v")),
                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
        );
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(IntType.INSTANCE, "v"),
                        new CompoundStatement(
                                new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                                        statementSecond
                                                                )
                                                        )
                                                ),
                                                statementSecond
                                        )
                                )
                        )
                )
        );
    }

    // Example 12: Example of typechecker errors
    private static IStatement getExample12() {
        return new CompoundStatement(
                new VariableDeclarationStatement(IntType.INSTANCE, "v"),
                new AssignmentStatement("v", new ValueExpression(new BooleanValue(true))) // Type error here
        );
    }

    //Example 13: Switch example
    private static IStatement getSwitchExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(IntType.INSTANCE, "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(IntType.INSTANCE, "b"),
                        new CompoundStatement(
                                new VariableDeclarationStatement(IntType.INSTANCE, "c"),
                                new CompoundStatement(
                                        new AssignmentStatement("a", new ValueExpression(new IntValue(1))),
                                        new CompoundStatement(
                                                new AssignmentStatement("b", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(
                                                        new AssignmentStatement("c", new ValueExpression(new IntValue(5))),
                                                        new CompoundStatement(
                                                                new SwitchStatement(
                                                                        new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new IntValue(10)), 3), // a*10
                                                                        new ArithmeticExpression(new VariableExpression("b"), new VariableExpression("c"), 3), // b*c
                                                                        new CompoundStatement(new PrintStatement(new VariableExpression("a")), new PrintStatement(new VariableExpression("b"))),
                                                                        new ValueExpression(new IntValue(10)),
                                                                        new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))), new PrintStatement(new ValueExpression(new IntValue(200)))),
                                                                        new PrintStatement(new ValueExpression(new IntValue(300)))
                                                                ),
                                                                new PrintStatement(new ValueExpression(new IntValue(300)))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    // Example 14: Semaphore example
    private static IStatement getSemaphoreExample() {
        CompoundStatement statementSecond = new CompoundStatement(
                new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                new ReleaseStatement("cnt")
        );
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v1"),
                new CompoundStatement(
                        new VariableDeclarationStatement(IntType.INSTANCE, "cnt"),
                        new CompoundStatement(
                                new HeapAllocationStatement("v1", new ValueExpression(new IntValue(1))),
                                new CompoundStatement(
                                        new CreateSemaphoreStatement("cnt", new ReadHeapExpression(new VariableExpression("v1"))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new AcquireStatement("cnt"),
                                                                new CompoundStatement(
                                                                        new HeapWriteStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), 3)), // * 10
                                                                        statementSecond
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new ForkStatement(
                                                                new CompoundStatement(
                                                                        new AcquireStatement("cnt"),
                                                                        new CompoundStatement(
                                                                                new HeapWriteStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), 3)), // * 10
                                                                                new CompoundStatement(
                                                                                        new HeapWriteStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(2)), 3)), // * 2
                                                                                        statementSecond
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompoundStatement(
                                                                new AcquireStatement("cnt"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)), 2)), // - 1
                                                                        new ReleaseStatement("cnt")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    //Example 15: For example
    private static IStatement getForExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "a"),
                new CompoundStatement(
                        new HeapAllocationStatement("a", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new CompoundStatement(
                                        new ForStatement("v",
                                                new ValueExpression(new IntValue(0)),
                                                new ValueExpression(new IntValue(3)),
                                                new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), 1), // v + 1
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new PrintStatement(new VariableExpression("v")),
                                                                new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ReadHeapExpression(new VariableExpression("a")), 3)) // v * rh(a)
                                                        )
                                                )
                                        ),
                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                ),
                                new NoOperationStatement()
                        )
                )
        );
    }

    //Example 16: Lock example
    private static IStatement getLockExample() {

        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v1"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v2"),
                        new CompoundStatement(
                                new VariableDeclarationStatement(IntType.INSTANCE, "x"),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(IntType.INSTANCE, "q"),
                                        new CompoundStatement(
                                                new HeapAllocationStatement("v1", new ValueExpression(new IntValue(20))),
                                                new CompoundStatement(
                                                        new HeapAllocationStatement("v2", new ValueExpression(new IntValue(30))),
                                                        new CompoundStatement(
                                                                new NewLockStatement("x"),
                                                                new CompoundStatement(
                                                                        new ForkStatement(
                                                                                new CompoundStatement(
                                                                                        new ForkStatement(
                                                                                                new CompoundStatement(
                                                                                                        new LockStatement("x"),
                                                                                                        new CompoundStatement(
                                                                                                                new HeapWriteStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(1)), 2)), // - 1
                                                                                                                new UnlockStatement("x")
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompoundStatement(
                                                                                                new LockStatement("x"),
                                                                                                new CompoundStatement(
                                                                                                        new HeapWriteStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), 3)), // * 10
                                                                                                        new UnlockStatement("x")
                                                                                                )
                                                                                        )
                                                                                )
                                                                        ),
                                                                        new CompoundStatement(
                                                                                new NewLockStatement("q"),
                                                                                new CompoundStatement(
                                                                                        new ForkStatement(
                                                                                                new CompoundStatement(
                                                                                                        new ForkStatement(
                                                                                                                new CompoundStatement(
                                                                                                                        new LockStatement("q"),
                                                                                                                        new CompoundStatement(
                                                                                                                                new HeapWriteStatement("v2", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(5)), 1)), // + 5
                                                                                                                                new UnlockStatement("q")
                                                                                                                        )
                                                                                                                )
                                                                                                        ),
                                                                                                        new CompoundStatement(
                                                                                                                new LockStatement("q"),
                                                                                                                new CompoundStatement(
                                                                                                                        new HeapWriteStatement("v2", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)), 3)), // * 10
                                                                                                                        new UnlockStatement("q")
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompoundStatement(
                                                                                                new NoOperationStatement(),
                                                                                                new CompoundStatement(
                                                                                                        new NoOperationStatement(),
                                                                                                        new CompoundStatement(
                                                                                                                new NoOperationStatement(),
                                                                                                                new CompoundStatement(
                                                                                                                        new NoOperationStatement(),
                                                                                                                        new CompoundStatement(
                                                                                                                                new LockStatement("x"),
                                                                                                                                new CompoundStatement(
                                                                                                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                                                                                        new CompoundStatement(
                                                                                                                                                new UnlockStatement("x"),
                                                                                                                                                new CompoundStatement(
                                                                                                                                                        new LockStatement("q"),
                                                                                                                                                        new CompoundStatement(
                                                                                                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
                                                                                                                                                                new UnlockStatement("q")
                                                                                                                                                        )
                                                                                                                                                )
                                                                                                                                        )
                                                                                                                                )
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    //Example 17: Repeat Until example
    private static IStatement getRepeatUntilExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(IntType.INSTANCE, "v"),
                new CompoundStatement(
                        new VariableDeclarationStatement(IntType.INSTANCE, "x"),
                        new CompoundStatement(
                                new VariableDeclarationStatement(IntType.INSTANCE, "y"),
                                new CompoundStatement(
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                                        new CompoundStatement(
                                                new CompoundStatement(
                                                        new RepeatUntilStatement(
                                                                new CompoundStatement(
                                                                        new ForkStatement(
                                                                                new CompoundStatement(
                                                                                        new PrintStatement(new VariableExpression("v")),
                                                                                        new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), 2)) // v - 1
                                                                                )
                                                                        ),
                                                                        new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), 1)) // v + 1
                                                                ),
                                                                new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), 3) // v == 3
                                                        ),
                                                        new CompoundStatement(
                                                                new AssignmentStatement("x", new ValueExpression(new IntValue(1))),
                                                                new CompoundStatement(
                                                                        new NoOperationStatement(),
                                                                        new CompoundStatement(
                                                                                new AssignmentStatement("y", new ValueExpression(new IntValue(3))),
                                                                                new CompoundStatement(
                                                                                        new NoOperationStatement(),
                                                                                        new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(10)), 3)) // v * 10
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new NoOperationStatement()
                                        )
                                )
                        )
                )
        );
    }

    //Example 18: CyclicBarrier example
    private static IStatement getCyclicBarrierExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v1"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v2"),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v3"),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(IntType.INSTANCE, "cnt"),
                                        new CompoundStatement(
                                                new HeapAllocationStatement("v1", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(
                                                        new HeapAllocationStatement("v2", new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(
                                                                new HeapAllocationStatement("v3", new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(
                                                                        // newBarrier(cnt, rH(v2)); -> capacity is 3
                                                                        new NewBarrierStatement("cnt", new ReadHeapExpression(new VariableExpression("v2"))),
                                                                        new CompoundStatement(
                                                                                // Fork 1: await(cnt); wh(v1,rh(v1)*10); print(rh(v1));
                                                                                new ForkStatement(
                                                                                        new CompoundStatement(
                                                                                                new AwaitStatement("cnt"),
                                                                                                new CompoundStatement(
                                                                                                        new HeapWriteStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), 3)), // * 10
                                                                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v1")))
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompoundStatement(
                                                                                        // Fork 2: await(cnt); wh(v2,rh(v2)*10); wh(v2,rh(v2)*10); print(rh(v2));
                                                                                        new ForkStatement(
                                                                                                new CompoundStatement(
                                                                                                        new AwaitStatement("cnt"),
                                                                                                        new CompoundStatement(
                                                                                                                new HeapWriteStatement("v2", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)), 3)), // 3 * 10 = 30
                                                                                                                new CompoundStatement(
                                                                                                                        new HeapWriteStatement("v2", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)), 3)), // 30 * 10 = 300
                                                                                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v2")))
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        // Main Thread: await(cnt); print(rh(v3));
                                                                                        new CompoundStatement(
                                                                                                new AwaitStatement("cnt"),
                                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v3")))
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    //Example 19: ConditionalAssignment
    private static IStatement getConditionalAssignmentExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "a"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "b"),
                        new CompoundStatement(
                                new VariableDeclarationStatement(IntType.INSTANCE, "v"),
                                new CompoundStatement(
                                        new HeapAllocationStatement("a", new ValueExpression(new IntValue(0))),
                                        new CompoundStatement(
                                                new HeapAllocationStatement("b", new ValueExpression(new IntValue(0))),
                                                new CompoundStatement(
                                                        new HeapWriteStatement("a", new ValueExpression(new IntValue(1))),
                                                        new CompoundStatement(
                                                                new HeapWriteStatement("b", new ValueExpression(new IntValue(2))),
                                                                new CompoundStatement(
                                                                        new ConditionalAssignmentStatement("v",
                                                                                new RelationalExpression(new ReadHeapExpression(new VariableExpression("a")), new ReadHeapExpression(new VariableExpression("b")), 1), // <
                                                                                new ValueExpression(new IntValue(100)),
                                                                                new ValueExpression(new IntValue(200))
                                                                        ),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new CompoundStatement(
                                                                                        new ConditionalAssignmentStatement("v",
                                                                                                new RelationalExpression(
                                                                                                        new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("b")), new ValueExpression(new IntValue(2)), 2), // - 2
                                                                                                        new ReadHeapExpression(new VariableExpression("a")),
                                                                                                        5 // >
                                                                                                ),
                                                                                                new ValueExpression(new IntValue(100)),
                                                                                                new ValueExpression(new IntValue(200))
                                                                                        ),
                                                                                        new PrintStatement(new VariableExpression("v"))
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    //Example 20: CountDownLatch
    private static IStatement getCountDownLatchExample() {
        return new CompoundStatement(
                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v1"),
                new CompoundStatement(
                        new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v2"),
                        new CompoundStatement(
                                new VariableDeclarationStatement(new RefType(IntType.INSTANCE), "v3"),
                                new CompoundStatement(
                                        new VariableDeclarationStatement(IntType.INSTANCE, "cnt"),
                                        new CompoundStatement(
                                                new HeapAllocationStatement("v1", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(
                                                        new HeapAllocationStatement("v2", new ValueExpression(new IntValue(3))),
                                                        new CompoundStatement(
                                                                new HeapAllocationStatement("v3", new ValueExpression(new IntValue(4))),
                                                                new CompoundStatement(
                                                                        // newLatch(cnt, rH(v2))
                                                                        new NewLatchStatement("cnt", new ReadHeapExpression(new VariableExpression("v2"))),
                                                                        new CompoundStatement(
                                                                                new ForkStatement(
                                                                                        new CompoundStatement(
                                                                                                new HeapWriteStatement("v1", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntValue(10)), 3)),
                                                                                                new CompoundStatement(
                                                                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                                                        new CountDownStatement("cnt")
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompoundStatement(
                                                                                        new ForkStatement(
                                                                                                new CompoundStatement(
                                                                                                        new HeapWriteStatement("v2", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntValue(10)), 3)),
                                                                                                        new CompoundStatement(
                                                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v2"))),
                                                                                                                new CountDownStatement("cnt")
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompoundStatement(
                                                                                                new ForkStatement(
                                                                                                        new CompoundStatement(
                                                                                                                new HeapWriteStatement("v3", new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v3")), new ValueExpression(new IntValue(10)), 3)),
                                                                                                                new CompoundStatement(
                                                                                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v3"))),
                                                                                                                        new CountDownStatement("cnt")
                                                                                                                )
                                                                                                        )
                                                                                                ),
                                                                                                new CompoundStatement(
                                                                                                        // UPDATED HERE:
                                                                                                        new LatchAwaitStatement("cnt"),
                                                                                                        new CompoundStatement(
                                                                                                                new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                                                new CompoundStatement(
                                                                                                                        new CountDownStatement("cnt"),
                                                                                                                        new PrintStatement(new ValueExpression(new IntValue(100)))
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static ProgramState createProgramState(IStatement statement) {
        MyIStack<IStatement> executionStack = new ListExecutionStack<>();
        ISymbolTable<String, IValue> symbolTable = new MapSymbolTable();
        MyIList<model.value.IValue> outputList = new ListOutput<>();
        IFileTable fileTable = new MapFileTable();
        IHeapTable heapTable = new MapHeapTable();
        ISemaphoreTable semaphoreTable = new SemaphoreTable();
        ILockTable lockTable = new LockTable();
        IBarrierTable barrierTable = new BarrierTable();
        ILatchTable latchTable = new LatchTable();
        int id = ProgramState.getAndIncrementLastId();
        return new ProgramState(
                symbolTable,
                executionStack,
                outputList,
                fileTable,
                heapTable,
                statement,
                semaphoreTable,
                lockTable,
                barrierTable,
                latchTable,
                id);
    }

    
}
