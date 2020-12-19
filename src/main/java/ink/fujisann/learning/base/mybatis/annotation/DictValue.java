package ink.fujisann.learning.base.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注的字段作为查询字典的valueCode字段
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DictValue {
    /**
     * 字典的typeCode字段
     *
     * @return 字典的typeCode字段
     */
    String typeCode();

    /**
     * 保存字典valueName的字段
     *
     * @return 保存字典valueName的字段
     */
    String valueNameField();
}
