package ink.fujisann.learning.base.intercept;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.SneakyThrows;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;

/**
 * mybatis拦截器
 * {@code @Signature 指定拦截的类，方法，方法的参数；拦截的类是mybatis部分核心类}
 *
 * @author raiRezon
 * @date 2020/11/16
 */
@Intercepts({
        //@Signature(
        //        type = Executor.class,
        //        method = "query",
        //        args = {
        //                MappedStatement.class,
        //                Object.class,
        //                RowBounds.class,
        //                ResultHandler.class
        //        }
        //),
        @Signature(
                type = ResultSetHandler.class,
                method = "handleResultSets",
                args = {
                        Statement.class
                }
        )
})
@Component
public class MybatisResult implements Interceptor {

    /**
     * 拦截方法执行逻辑
     *
     * @param invocation 拦截点
     * @return sql处理后的结果
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 被拦截的方法执行前
        Object result = invocation.proceed();
        // 被拦截的方法执行后
        modifyResult(result);
        return result;
    }

    @SneakyThrows
    private void modifyResult(Object result) {
        if (result instanceof List<?>) {
            List<?> list = (List<?>) result;
            for (Object o : list) {
                //Field[] fields = o.getClass().getDeclaredFields();
                PropertyDescriptor descriptor = new PropertyDescriptor("setUserName", o.getClass());
                Method writeMethod = descriptor.getWriteMethod();
                writeMethod.invoke(o, "哈哈哈");
            }
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
