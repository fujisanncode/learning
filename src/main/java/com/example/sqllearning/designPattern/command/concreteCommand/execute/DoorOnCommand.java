package com.example.sqllearning.designPattern.command.concreteCommand.execute;

import com.example.sqllearning.designPattern.command.receiver.Door;

public class DoorOnCommand implements Command {

    private Door door;

    public DoorOnCommand(Door door) {
        this.door = door;
    }

    @Override
    public void execute() {
        door.openDoor();
    }
}
