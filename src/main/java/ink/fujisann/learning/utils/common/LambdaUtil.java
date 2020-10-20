package ink.fujisann.learning.utils.common;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @program: sqlleaning
 * @description: 自定义lambda表达式
 * @author: hulei
 * @create: 2020-03-17 00:11:51
 */
public class LambdaUtil {

    /**
     * @description 定义带下表的consumer
     * @param consumer 自定义行为的consumer定义
     * @return java.util.function.Consumer<T>
     * @author hulei
     * @date 2020-03-17 00:56:52
     */
    public static <T> Consumer<T> withIndex(BiConsumer<T, Integer> consumer) {
        class Obj {

            int i = 0;
        }
        Obj obj = new Obj();
        return t -> {
            consumer.accept(t, ++obj.i);
        };
    }
}
