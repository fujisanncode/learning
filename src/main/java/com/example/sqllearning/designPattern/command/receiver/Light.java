package com.example.sqllearning.designPattern.command.receiver;

import lombok.extern.slf4j.Slf4j;

// 接受命令的对象
@Slf4j
public class Light {

    public void openLight() {
        log.info("打开电灯");
    }

    public void closeLight() {
        log.info("关闭电灯");
    }
}
