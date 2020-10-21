package ink.fujisann.learning.base.designPattern.command.invoke;

import ink.fujisann.learning.base.designPattern.command.concreteCommand.execute.Command;

public class SimpleControl {

    private Command[] onCommands = new Command[5];
    private Command[] offCommands = new Command[5];

    // 需要哪个对象执行，就传入其命令对象
    public void setCommand(int slot, Command onCommand, Command offCommand) {
        this.onCommands[slot] = onCommand;
        this.offCommands[slot] = offCommand;
    }

    // 客户端执行命令只需要执行此方法，不需要关注命令最终是哪个对象执行
    public void pressOnButton(int slot) {
        onCommands[slot].execute();
    }

    // 客户端执行命令只需要执行此方法，不需要关注命令最终是哪个对象执行
    public void pressOffButton(int slot) {
        offCommands[slot].execute();
    }
}
