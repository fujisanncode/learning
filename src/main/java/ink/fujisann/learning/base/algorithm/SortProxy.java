package ink.fujisann.learning.base.algorithm;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * @description: 代理排序对象
 * @author: hulei
 * @create: 2020-06-08 22:43:31
 */
@Slf4j
public class SortProxy implements MethodInterceptor {

  /**
   * @description 被代理方法的基础上增加日志记录
   * @param o
   * @param method
   * @param objects
   * @param methodProxy
   * @return java.lang.Object
   * @author hulei
   * @date 2020-06-08 22:52:10
   */
  @Override
  public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
    long start = System.currentTimeMillis();
    // 调用父类的方法，cglib生成的是被代理类的子类
    Object returnObj = methodProxy.invokeSuper(o, objects);
    log.info("cost time {}ms", System.currentTimeMillis() - start);
    return returnObj;
  }
}
