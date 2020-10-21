package ink.fujisann.learning.base.designPattern.command.concreteCommand.execute;

import ink.fujisann.learning.base.designPattern.command.receiver.Light;

public class LightOffCommand implements Command {

    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.closeLight();
    }
}
