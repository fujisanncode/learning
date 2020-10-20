package ink.fujisann.learning.designPattern.command.receiver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Door {

    public void openDoor() {
        log.info("打开门");
    }

    public void closeDoor() {
        log.info("关闭门");
    }
}
