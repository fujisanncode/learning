package ink.fujisann.learning.base.designPattern.proxy.cglib;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookImpl {

    public void add() {
        log.info("add book");
    }
}
