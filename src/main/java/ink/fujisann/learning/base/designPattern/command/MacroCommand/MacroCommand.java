package ink.fujisann.learning.base.designPattern.command.MacroCommand;

import ink.fujisann.learning.base.designPattern.command.concreteCommand.execute.Command;

// 批处理命令
public class MacroCommand implements Command {

    private Command[] Commands;

    public MacroCommand(Command[] Commands) {
        // 初始化宏命令
        this.Commands = Commands;
    }

    @Override
    public void execute() {
        // 批量执行命令
        for (Command Command : Commands) { Command.execute(); }
    }
}
