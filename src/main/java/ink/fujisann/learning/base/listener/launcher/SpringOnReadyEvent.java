package ink.fujisann.learning.base.listener.launcher;

import org.springframework.context.ApplicationEvent;

public class SpringOnReadyEvent extends ApplicationEvent {
    /**
     * 子类默认构造无参数，父类没有无参数构造<br/>
     * 所以如果子类用无参数构造继承父类有参数构造会报错，不要定义一个有参数构造<br/>
     *
     * @param source
     */
    public SpringOnReadyEvent(Object source) {
        super(source);
    }
}
