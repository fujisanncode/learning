package ink.fujisann.learning.base.designPattern.command.concreteCommand.undo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoCommand implements CommandWithUndo {

    @Override
    public void execute() {
        log.info("没有设置命令");
    }

    @Override
    public void undo() {
        log.info("没有取消命令");
    }
}
