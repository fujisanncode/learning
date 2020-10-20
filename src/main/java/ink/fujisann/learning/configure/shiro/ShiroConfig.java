package ink.fujisann.learning.configure.shiro;

import ink.fujisann.learning.configure.shiro.filter.MyFormAuthenticationFilter;
import ink.fujisann.learning.configure.shiro.filter.MyPermissionAuthorizationFilter;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * @description: shiro过滤器配置，安全配置等
 * @author: hulei
 * @create: 2020-06-06 19:11:07
 */
@Slf4j
@Configuration
public class ShiroConfig {

  // private ShiroProperty shiroProperty;
  //
  // @Autowired
  // public void setShiroProperty(ShiroProperty shiroProperty) {
  //     this.shiroProperty = shiroProperty;
  // }

  @Value ("${local.redis.host}")
  private String redisHost;
  @Value ("${local.redis.port}")
  private String redisPort;
  @Value ("${local.redis.name}")
  private String redisName;
  @Value ("${local.redis.pass}")
  private String redisPass;

  /**
   * @description ???
   * @return org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
   * @author hulei
   * @date 2020-03-14 16:41:14
   */
  @Bean
  @ConditionalOnMissingBean

  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
    proxyCreator.setProxyTargetClass(true);
    return proxyCreator;
  }

  /**
   * @description ???
   * @param securityManager 容器中的安全管理器
   * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
   * @author hulei
   * @date 2020-03-14 16:41:37
   */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor auth = new AuthorizationAttributeSourceAdvisor();
    auth.setSecurityManager(securityManager);
    return auth;
  }

  /**
   * @description 注入自定义的校验域
   * @return realm
   * @author hulei
   * @date 2020-03-14 16:37:58
   */
  @Bean
  public Realm customRealm() {
    return new MyRealm();
  }

  /**
   * @description 注入自定义的sessionManage
   * @return org.apache.shiro.session.mgt.SessionManager
   * @author hulei
   * @date 2020-03-14 16:38:58
   */
  @Bean
  public SessionManager getMySessionManage() {
    return new MySessionManage();
  }

  /**
   * @description 注入安全管理器
   * @return org.apache.shiro.mgt.SecurityManager
   * @author hulei
   * @date 2020-03-14 16:39:39
   */
  @Bean
  public SecurityManager defaultSecurityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    // 指定校验的域
    securityManager.setRealm(customRealm());
    try (Jedis jedis = new Jedis(redisHost, Integer.parseInt(redisPort));) {
      jedis.get("");
      // 如果配置本机安装了redis，使用redis作为缓存，和会话管理器
      securityManager.setCacheManager(myRedisCacheManager());
      securityManager.setSessionManager(myRedisSessionManager());
    } catch (Exception e) {
      log.error("连接到redis失败");
      // 指定sessionManage
      securityManager.setSessionManager(getMySessionManage());
    }

    return securityManager;
  }

  /**
   * @description 获取redisCache
   * @return org.crazycake.shiro.RedisCacheManager
   * @author hulei
   * @date 2020-05-15 17:00:47
   */
  public RedisCacheManager myRedisCacheManager() {
    RedisCacheManager redisCacheManager = new RedisCacheManager();
    redisCacheManager.setRedisManager(myRedisManager());
    return redisCacheManager;
  }

  /**
   * @description 连接redis
   * @return org.crazycake.shiro.RedisManager
   * @author hulei
   * @date 2020-05-15 17:00:05
   */
  public RedisManager myRedisManager() {
    RedisManager redisManager = new RedisManager();
    redisManager.setHost(redisHost + ":" + redisPort);
    redisManager.setPassword(redisPass);
    return redisManager;
  }

  /**
   * @description 配置redis版本的sessionManager
   * @return org.apache.shiro.session.mgt.SessionManager
   * @author hulei
   * @date 2020-05-15 17:03:16
   */
  public SessionManager myRedisSessionManager() {
    DefaultSessionManager sessionManager = new DefaultWebSessionManager();
    sessionManager.setSessionDAO(myRedisSessionDao());
    sessionManager.setCacheManager(myRedisCacheManager());
    return sessionManager;
  }

  /**
   * @description 设置数据源为redis
   * @return org.apache.shiro.session.mgt.eis.SessionDAO
   * @author hulei
   * @date 2020-05-15 17:07:46
   */
  public SessionDAO myRedisSessionDao() {
    RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
    redisSessionDAO.setRedisManager(myRedisManager());
    return redisSessionDAO;
  }

  /**
   * @description 配置过滤器，安全管理器
   * @param securityManager 容器中的安全管理器(写入拦截器工厂)
   * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
   * @author hulei
   * @date 2020-03-14 16:40:14
   */
  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
    // 指定安全管理器
    filterFactory.setSecurityManager(securityManager);
    /*
     * @description 重写默认拦截过滤器，中特定行为的url
     * 1、setLoginUrl，没有登陆跳转到这个接口
     * 2、setUnauthorizedUrl，没有权限跳转到这个接口
     * 2、登录成功调转到这个接口(前后分离不需要这个，登录成功传递给页面session即可)
     */
    // filterFactory.setLoginUrl("/shiro-manage/noLogin");
    // filterFactory.setUnauthorizedUrl("/shiro-manage/unAuth");
    // filterFactory.setSuccessUrl("/shiro-manage/after-login");
    /*
     * @description 重写默认的拦截过滤器
     * 1、DefaultFilter中是shiro默认的所有过滤器
     * 2、自定义登录认证过滤器的行为(重新被authc拦截器的行为)
     * 3、自定义拦截器的别名和下述拦截规则对应
     */
    filterFactory.getFilters().put("authc", new MyFormAuthenticationFilter("/shiro-manage/noLogin"));
    filterFactory.getFilters().put("perms", new MyPermissionAuthorizationFilter());
    /*
     * 拦截规则定义(什么接口走什么过滤器)
     * 1、chainMap定义拦截规则，authc表示需要通过登录认证过滤器，anon表示需要通过匿名拦截器(即不需要拦截)
     * 2、perms表示需要通过权限拦截过滤器(需要指定参数，即权限)；权限拦截也可以通过注解的方式进行@RequiresPermissions
     * 3、配置需要拦截的ur(authc需要拦截，anon不需要拦截，需要拦截写在前面)，解释如下：
     *    拦截规则会按照下述定义依次执行，例如A接口定义为anon->authc，则先走anon然后走authc过滤器，则会被拦截
     * 4、配置拦截规则和拦截过滤器，和配置注解是通过不同的逻辑进行判断的；优先按照拦截规则进行拦截
     */
    Map<String, String> chainMap = new HashMap<>();
    // chainMap.put("/shiro-manage/findAllUser", "perms[/shiro-manage/findAllUser]");
    // chainMap.put("/**", "authc"); // 所有接口需要登录
    // chainMap.put("/shiro-manage/index", "perms");
    chainMap.put("/shiro-manage/login", "anon"); // 除了登入接口
    chainMap.put("/hello/say-hello", "anon"); // 除了登入接口
    chainMap.put("/shiro-manage/logout", "anon"); // 除了登录接口
    filterFactory.setFilterChainDefinitionMap(chainMap);
    return filterFactory;
  }
}
