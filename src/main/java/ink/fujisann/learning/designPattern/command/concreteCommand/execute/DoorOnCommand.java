package ink.fujisann.learning.designPattern.command.concreteCommand.execute;

import ink.fujisann.learning.designPattern.command.receiver.Door;

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
