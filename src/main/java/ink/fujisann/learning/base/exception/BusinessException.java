package ink.fujisann.learning.base.exception;

import ink.fujisann.learning.base.utils.MessageUtil;
import lombok.Getter;
import lombok.ToString;

/**
 * 抛出全局异常
 *
 * @author hulei
 * @date 2020-10-23 22:04:05
 */
@SuppressWarnings("unused")
@Getter
@ToString
public class BusinessException extends RuntimeException {

    /**
     * 业务异常码
     */
    private final String code;

    /**
     * 业务异常原因
     */
    private final String msg;

    /**
     * 不传异常码，按系统错误码构造异常对象
     */
    public BusinessException() {
        this.code = "system_exception";
        this.msg = MessageUtil.getMessage(this.code);
    }

    /**
     * 传入i18n中定义的异常码，构造异常对象
     *
     * @param code 异常码
     */
    public BusinessException(String code) {
        this.code = code;
        this.msg = MessageUtil.getMessage(this.code);
    }

    /**
     * 外部类提供私有构造方法，供内部类构造外部类
     *
     * @param builder 内部类
     */
    private BusinessException(Builder builder) {
        this.code = builder.code;
        this.msg = builder.msg;
    }

    /**
     * 静态内部类 通过set方法 和build方法构造外部类
     *
     * @author hulei
     * @date 2020-10-23 21:42:10
     */
    public static class Builder {

        private String code;
        private String msg;

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public BusinessException build() {
            return new BusinessException(this);
        }
    }
}
