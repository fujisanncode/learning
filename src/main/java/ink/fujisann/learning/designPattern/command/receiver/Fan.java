package ink.fujisann.learning.designPattern.command.receiver;

import lombok.extern.slf4j.Slf4j;

// 接受者 电扇接受各种命令
@Slf4j
public class Fan {

    private final static int HIGH = 3;
    private final static int MEDIUM = 2;
    private final static int LOW = 1;
    private final static int OFF = 0;

    private int speed;

    public Fan() {
        this.speed = OFF;
    }

    public void off() {
        this.speed = OFF;
        log.info("关闭开关");
    }

    public void low() {
        this.speed = LOW;
        log.info("打开低速开关");
    }

    public void medium() {
        this.speed = MEDIUM;
        log.info("打开中速开关");
    }

    public void high() {
        this.speed = HIGH;
        log.info("打开高速开关");
    }

    public int getSpeed() {
        return this.speed;
    }
}
