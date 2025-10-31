package view;

import model.statement.IStatement;

public interface IView {
    void printMenu();
    void runMenu();
    void runProgram(IStatement program);
}
