package ink.fujisann.learning.base.designPattern.proxy.stati;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 静态代理模式下， 目标类需要实现和代理类相同的接口，如果接口新增方法，目标类也需要修改，
// 动态代理则不需要修改目标类，因为动态代理模式不需要实现代理类的接口
public class ProxyCloth implements Cloth {

    // 保存被代理对象
    private Cloth cloth;

    public ProxyCloth(Cloth cloth) {
        this.cloth = cloth;
    }

    @Override
    public void getName() {
        log.info("proxy cloth");
        cloth.getName();
    }
}
