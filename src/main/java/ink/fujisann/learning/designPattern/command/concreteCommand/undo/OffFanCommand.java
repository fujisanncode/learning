package ink.fujisann.learning.designPattern.command.concreteCommand.undo;

import ink.fujisann.learning.designPattern.command.receiver.Fan;

// 高度风扇命令
public class OffFanCommand implements CommandWithUndo {

    private Fan fan;
    private int preSpeed;

    public OffFanCommand(Fan fan) {
        this.fan = fan;
    }

    @Override
    public void execute() {
        // 将风扇设置为高速风扇前先记录风扇的速度
        preSpeed = fan.getSpeed();
        fan.off();
    }

    @Override
    public void undo() {
        // 风扇撤销到上次记录的状态
        switch (this.preSpeed) {
            case 1:
                fan.low();
                break;
            case 2:
                fan.medium();
                break;
            case 3:
                fan.high();
                break;
            default:
                fan.off();
                break;
        }
    }
}
