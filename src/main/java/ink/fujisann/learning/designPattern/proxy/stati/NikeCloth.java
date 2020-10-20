package ink.fujisann.learning.designPattern.proxy.stati;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NikeCloth implements Cloth {

    @Override
    public void getName() {
        log.info("this is nike cloth");
    }
}
