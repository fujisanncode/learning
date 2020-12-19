package ink.fujisann.learning.base.mybatis.interceptor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ink.fujisann.learning.base.mybatis.annotation.DictValue;
import ink.fujisann.learning.base.mybatis.annotation.NeedDict;
import ink.fujisann.learning.code.dao.CommonDictMapper;
import ink.fujisann.learning.code.pojo.mybatis.CommonDict;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * mybatis拦截器
 * {@code @Signature 指定拦截的类，方法，方法的参数；拦截的类是mybatis部分核心类}
 *
 * @author raiRezon
 * @date 2020/11/16
 */
@Slf4j
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
@Component
public class MyResultSetInterceptor implements Interceptor {

    /**
     * 拦截方法执行逻辑
     *
     * @param invocation 拦截点
     * @return sql处理后的结果
     * @throws Throwable 执行目标方法可能抛出异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.debug("进入mybatis拦截器");

        //执行目标方法，获取执行结果
        ResultSetHandler target = (ResultSetHandler) invocation.getTarget();
        Object[] args = invocation.getArgs();
        Statement statement = (Statement) args[0];
        List<Object> result = target.handleResultSets(statement);

        // 如果返回结果为空，则直接返回
        if (CollectionUtils.isEmpty(result)) {
            return result;
        }

        // 如果返回对象上无字典查询的注解，则直接返回
        Object o = result.get(0);
        Class<?> aClass = o.getClass();
        NeedDict needDict = aClass.getAnnotation(NeedDict.class);
        if (Objects.isNull(needDict)) {
            return result;
        }

        // 如果返回结果的类需要查询字典
        List<DictField> dictFieldList = new ArrayList<>();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            DictValue dictValue = field.getAnnotation(DictValue.class);
            if (!Objects.isNull(dictValue)) {
                // 所有需要查询字典的字段名称保存起来，用户后面遍历查询字典
                DictField dictField = new DictField() {{
                    setValueCodeFieldName(field.getName());
                    setValueNameFieldName(dictValue.valueNameField());
                    setTypeCode(dictValue.typeCode());
                }};
                dictFieldList.add(dictField);
            }
        }

        // 按typeCode列表查询字典
        List<String> typeCodeList = dictFieldList.stream().map(DictField::getTypeCode)
                .collect(Collectors.toList());
        QueryWrapper<CommonDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("typeCode", typeCodeList);
        // 拦截器中不能通过注入的方式注入mapper
        CommonDictMapper commonDictMapper = applicationContext.getBean(CommonDictMapper.class);
        List<CommonDict> commonDictList = commonDictMapper.selectList(queryWrapper);
        // 字典查询结果转为map，便于后面查询使用
        Map<String, Map<String, String>> dictMap = commonDictList.stream()
                .collect(Collectors.groupingBy(CommonDict::getTypeCode,
                        Collectors.toMap(CommonDict::getValueCode, CommonDict::getValueValue)));

        // 遍历每一个对象, 将对象中需要查询字典赋值的字段进行赋值
        for (Object tmp : result) {
            for (DictField dictField : dictFieldList) {
                Class<?> tmpClass = tmp.getClass();
                Field valueCodeField = tmpClass.getDeclaredField(dictField.getValueCodeFieldName());
                valueCodeField.setAccessible(true);
                String valueCode = (String) valueCodeField.get(tmp);
                Field valueNameField = tmpClass.getDeclaredField(dictField.getValueNameFieldName());
                valueNameField.setAccessible(true);
                // valueName字段设置值
                valueNameField.set(tmp, dictMap.get(dictField.getTypeCode()).get(valueCode));
            }
        }
        return result;
    }

    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
