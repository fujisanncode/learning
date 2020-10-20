package com.example.sqllearning.designPattern.command.concreteCommand.undo;

public interface CommandWithUndo {

    void execute();

    void undo();

}
