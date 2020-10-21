package ink.fujisann.learning.base.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BusinessException extends RuntimeException {

    // 请求状态码
    private int status;
    // 业务异常码
    private String code;
    // 业务异常原因
    private String msg;

    public BusinessException() {
        super();
    }

    public BusinessException(ExceptionBuilder builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.status = builder.status;
    }

    // 静态内部类 通过set方法 和build方法构造外部类
    public static class ExceptionBuilder {

        private String code;
        private String msg;
        private int status;

        public ExceptionBuilder() {
            // 默认接口状态码为200
            this.status = 200;
        }

        public ExceptionBuilder setStatus(int status) {
            this.status = status;
            return this;
        }

        public ExceptionBuilder setCode(String code) {
            this.code = code;
            return this;
        }

        public ExceptionBuilder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public BusinessException build() {
            return new BusinessException(this);
        }
    }
}
