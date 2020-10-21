package ink.fujisann.learning.base.exception;

import lombok.Getter;

@Getter
public enum BusinessExceptionEnum {
    EXCEPTION_ERROR("E000", "EXCEPTION_ERROR"),
    EXCEPTION_PARA("E001", "EXCEPTION_PARA"),
    EXCEPTION_SQL("E002", "EXCEPTION_SQL");

    private String code;
    private String msg;

    // 枚举类型的构造 枚举类型结构的基础
    BusinessExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
