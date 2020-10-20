package com.example.sqllearning.designPattern.command.concreteCommand.execute;

import com.example.sqllearning.designPattern.command.receiver.Light;

// 命令实体
public class LightOnCommand implements Command {

    // 接受命令对象（聚合关系）
    private Light light;

    // 命令实体和接受命令对象是聚合关系（依赖关系）
    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.openLight();
    }
}
