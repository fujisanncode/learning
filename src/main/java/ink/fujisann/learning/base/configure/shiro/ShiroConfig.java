package ink.fujisann.learning.base.configure.shiro;

import ink.fujisann.learning.base.configure.shiro.filter.MyFormAuthenticationFilter;
import ink.fujisann.learning.base.configure.shiro.filter.MyPermissionAuthorizationFilter;
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

import java.util.HashMap;
import java.util.Map;

/**
 * shiro配置类<br/>
 * 配置特定接口是否需要拦截<br/>
 * 配置shiro的缓存,此处使用redis<br/>
 * 指定shiro使用的realm(数据领域，即数据库)<br/>
 * 指定sessionId传递策略，此处设置为优先从请求头的token字段获取<br/>
 *
 * @author hulei
 * @date 2020-06-06 19:11:07
 */
@Slf4j
@Configuration
public class ShiroConfig {

  @Value ("${local.redis.host}")
  private String redisHost;

  @Value ("${local.redis.port}")
  private String redisPort;

  @Value ("${local.redis.name}")
  private String redisName;

  @Value ("${local.redis.pass}")
  private String redisPass;

  /**
   * ???
   *
   * @return org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
   */
  @Bean
  @ConditionalOnMissingBean
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
    proxyCreator.setProxyTargetClass(true);
    return proxyCreator;
  }

  /**
   * ???
   * @param securityManager 容器中的安全管理器
   * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
   */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor auth = new AuthorizationAttributeSourceAdvisor();
    auth.setSecurityManager(securityManager);
    return auth;
  }

  /**
   * 注入自定义的校验域
   * @return realm
   */
  @Bean
  public Realm customRealm() {
    return new MyRealm();
  }

  /**
   * 注入自定义的sessionManage
   * @return org.apache.shiro.session.mgt.SessionManager
   */
  @Bean
  public SessionManager getMySessionManage() {
    return new MySessionManage();
  }

  /**
   * 注入安全管理器
   * @return org.apache.shiro.mgt.SecurityManager
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
   * 获取redisCache
   * @return org.crazycake.shiro.RedisCacheManager
   */
  public RedisCacheManager myRedisCacheManager() {
    RedisCacheManager redisCacheManager = new RedisCacheManager();
    redisCacheManager.setRedisManager(myRedisManager());
    return redisCacheManager;
  }

  /**
   * 连接redis
   * @return org.crazycake.shiro.RedisManager
   */
  public RedisManager myRedisManager() {
    RedisManager redisManager = new RedisManager();
    redisManager.setHost(redisHost + ":" + redisPort);
    redisManager.setPassword(redisPass);
    return redisManager;
  }

  /**
   * 配置redis版本的sessionManager
   * @return org.apache.shiro.session.mgt.SessionManager
   */
  public SessionManager myRedisSessionManager() {
    DefaultSessionManager sessionManager = new DefaultWebSessionManager();
    sessionManager.setSessionDAO(myRedisSessionDao());
    sessionManager.setCacheManager(myRedisCacheManager());
    return sessionManager;
  }

  /**
   * 设置数据源为redis
   * @return org.apache.shiro.session.mgt.eis.SessionDAO
   */
  public SessionDAO myRedisSessionDao() {
    RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
    redisSessionDAO.setRedisManager(myRedisManager());
    return redisSessionDAO;
  }

  /**
   * shiro配置<br/>
   * 1、指定安全管理器<br/>
   * 2、指定未登录时，重定向的接口<br/>
   * 3、指定特殊接口过滤链<br/>
   * 3.1、登入、登出、hello的测试接口不需要鉴权<br/>
   * 3.2、未添加shiro注解的接口不需要鉴权<br/>
   *
   * @param securityManager 容器中的安全管理器
   * @return shiro过滤器工厂
   */
  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
    // 指定安全管理器
    filterFactory.setSecurityManager(securityManager);
    /*
     * 重写默认拦截过滤器，中特定行为的url
     * 1、setLoginUrl，没有登陆跳转到这个接口
     * 2、setUnauthorizedUrl，没有权限跳转到这个接口
     * 2、登录成功调转到这个接口(前后分离不需要这个，登录成功传递给页面session即可)
     */
    // filterFactory.setLoginUrl("/shiro-manage/noLogin");
    // filterFactory.setUnauthorizedUrl("/shiro-manage/unAuth");
    // filterFactory.setSuccessUrl("/shiro-manage/after-login");
    /*
     * 重写默认的拦截过滤器
     * 1、DefaultFilter中是shiro默认的所有过滤器
     * 2、自定义登录认证过滤器的行为(重新被authc拦截器的行为)
     * 3、自定义拦截器的别名和下述拦截规则对应
     */
    filterFactory.getFilters().put("authc", new MyFormAuthenticationFilter("/shiro-manage/noLogin"));
    filterFactory.getFilters().put("perms", new MyPermissionAuthorizationFilter());
    /*
     * 拦截规则定义(什么接口走什么过滤器)
     * 1、chainMap定义拦截规则，authc表示需要通过登录认证过滤器，anon表示需要通过匿名拦截器(即不需要拦截)
     * 2、perms表示需要通过权限拦截过滤器(需要指定参数，即权限)；权限拦截也可以通过注解的方式进行@RequiresPermissions, 所以权限拦截不需要特殊指定
     * 3、配置需要拦截的ur(authc需要拦截，anon不需要拦截，需要拦截写在前面)，解释如下：
     *    拦截规则会按照下述定义依次执行，例如A接口定义为anon->authc，则先走anon然后走authc过滤器，则会被拦截
     * 4、配置拦截规则和拦截过滤器，和配置注解是通过不同的逻辑进行判断的；优先按照拦截规则进行拦截
     */
    // chainMap.put("/shiro-manage/findAllUser", "perms[/shiro-manage/findAllUser]");
    Map<String, String> chainMap = new HashMap<>(4);
    chainMap.put("/**", "authc");
    chainMap.put("/shiro-manage/logout", "anon");
    chainMap.put("/shiro-manage/login", "anon");
    chainMap.put("/hello/helloWithoutShiro", "anon");
    filterFactory.setFilterChainDefinitionMap(chainMap);
    return filterFactory;
  }
}
