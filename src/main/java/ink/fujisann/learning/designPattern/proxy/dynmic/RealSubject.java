package ink.fujisann.learning.designPattern.proxy.dynmic;

import lombok.extern.slf4j.Slf4j;

// 目标对象
@Slf4j
public class RealSubject implements Subject {

    @Override
    public void action() {
        log.info("realSubject -> action");
    }
}
