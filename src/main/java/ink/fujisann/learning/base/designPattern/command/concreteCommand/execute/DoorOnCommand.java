package ink.fujisann.learning.base.designPattern.command.concreteCommand.execute;

import ink.fujisann.learning.base.designPattern.command.receiver.Door;

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
