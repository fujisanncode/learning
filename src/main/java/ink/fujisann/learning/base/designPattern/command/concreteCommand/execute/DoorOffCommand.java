package ink.fujisann.learning.base.designPattern.command.concreteCommand.execute;

import ink.fujisann.learning.base.designPattern.command.receiver.Door;

public class DoorOffCommand implements Command {

    // 接受命令的对象
    private Door door;

    // 初始化关闭门的命令
    public DoorOffCommand(Door door) {
        this.door = door;
    }

    @Override
    public void execute() {
        door.closeDoor();
    }
}
