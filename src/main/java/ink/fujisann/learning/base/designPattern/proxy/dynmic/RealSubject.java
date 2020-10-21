package ink.fujisann.learning.base.designPattern.proxy.dynmic;

import lombok.extern.slf4j.Slf4j;

// 目标对象
@Slf4j
public class RealSubject implements ProxySubject {

    @Override
    public void action() {
        log.info("realSubject -> action");
    }
}
