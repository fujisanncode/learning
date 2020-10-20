package com.example.sqllearning.designPattern;

import com.example.sqllearning.SqlLearningApplication;
import com.example.sqllearning.designPattern.command.MacroCommand.MacroCommand;
import com.example.sqllearning.designPattern.command.concreteCommand.execute.Command;
import com.example.sqllearning.designPattern.command.concreteCommand.execute.DoorOffCommand;
import com.example.sqllearning.designPattern.command.concreteCommand.execute.DoorOnCommand;
import com.example.sqllearning.designPattern.command.concreteCommand.execute.LightOffCommand;
import com.example.sqllearning.designPattern.command.concreteCommand.execute.LightOnCommand;
import com.example.sqllearning.designPattern.command.concreteCommand.undo.CommandWithUndo;
import com.example.sqllearning.designPattern.command.concreteCommand.undo.HighFanCommand;
import com.example.sqllearning.designPattern.command.concreteCommand.undo.OffFanCommand;
import com.example.sqllearning.designPattern.command.invoke.SimpleControl;
import com.example.sqllearning.designPattern.command.invoke.SimpleControlWithUndo;
import com.example.sqllearning.designPattern.command.receiver.Door;
import com.example.sqllearning.designPattern.command.receiver.Fan;
import com.example.sqllearning.designPattern.command.receiver.Light;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest (classes = SqlLearningApplication.class)
public class CommandTest {

    @Test
    public void testController() {
        SimpleControl simpleControl = new SimpleControl();

        // 设置打开电灯的命令
        com.example.sqllearning.designPattern.command.concreteCommand.execute.Command lightCommand = new LightOnCommand(new Light());
        simpleControl.setCommand(0, lightCommand, null);
        simpleControl.pressOnButton(0);

        // 设置打开门的命令
        com.example.sqllearning.designPattern.command.concreteCommand.execute.Command doorCommand = new DoorOnCommand(new Door());
        simpleControl.setCommand(0, doorCommand, null);
        simpleControl.pressOnButton(0);
    }

    @Test
    public void testCommandUndo() {
        SimpleControlWithUndo simpleControlWithUndo = new SimpleControlWithUndo();
        Fan fan = new Fan();
        CommandWithUndo commandOn = new HighFanCommand(fan);
        CommandWithUndo commandOff = new OffFanCommand(fan);
        simpleControlWithUndo.setCommand(commandOn, commandOff);
        // 打开高速开关
        simpleControlWithUndo.pressButton();
        // 关闭高速开关
        simpleControlWithUndo.pressButtOff();
        // 取消上次执行的命令
        simpleControlWithUndo.pressButtUndo();
    }

    @Test
    public void testMacroCommand() {
        // 接受命令的实体(接受者)
        Light light = new Light();
        Door door = new Door();

        Command lightOnCommand = new LightOnCommand(light);
        Command lightOffCommand = new LightOffCommand(light);
        Command doorOnCommand = new DoorOnCommand(door);
        Command doorOffCommand = new DoorOffCommand(door);

        // 设置两个宏命令，批量控制开关
        Command[] onCommands = {lightOnCommand, doorOnCommand};
        MacroCommand onMacroCommand = new MacroCommand(onCommands);
        Command[] offCommands = {lightOffCommand, doorOffCommand};
        MacroCommand offMacroCommand = new MacroCommand(offCommands);

        // 调用者中加载命令组
        SimpleControl simpleControl = new SimpleControl();
        simpleControl.setCommand(0, onMacroCommand, offMacroCommand);
        // 调用者执行命令(宏命令)
        simpleControl.pressOnButton(0);
        simpleControl.pressOffButton(0);

    }
}
