package ink.fujisann.learning.designPattern.command.invoke;

import ink.fujisann.learning.designPattern.command.concreteCommand.undo.CommandWithUndo;
import ink.fujisann.learning.designPattern.command.concreteCommand.undo.NoCommand;

public class SimpleControlWithUndo {

    private CommandWithUndo commandOn;
    private CommandWithUndo commandOff;
    private CommandWithUndo commandUndo;

    public SimpleControlWithUndo() {
        CommandWithUndo noCommand = new NoCommand();
        // 三种命令模式都默认设置为非命令模式
        commandOn = noCommand;
        commandOff = noCommand;
        commandUndo = noCommand;
    }

    // 需要哪个对象执行，就传入其命令对象
    public void setCommand(CommandWithUndo commandOn, CommandWithUndo commandOff) {
        this.commandOn = commandOn;
        this.commandOff = commandOff;
    }

    // 打开
    public void pressButton() {
        commandOn.execute();
        commandUndo = commandOn;
    }

    // 关闭
    public void pressButtOff() {
        commandOff.execute();
        commandUndo = commandOff;
    }

    // 撤销 执行撤销前追踪到上一次执行的命令
    public void pressButtUndo() {
        commandUndo.undo();
    }
}
