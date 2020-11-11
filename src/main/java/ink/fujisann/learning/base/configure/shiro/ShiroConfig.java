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

  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private String redisPort;

  @Value("${spring.redis.password}")
  private String redisPass;

  /**
   * ???
   * {@code @ConditionalOnMissingBean} 修饰@Bean，如果bean存在多个实现会报错
   *
   * @return DefaultAdvisorAutoProxyCreator
   */
  @ConditionalOnMissingBean
  @Bean
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
    proxyCreator.setProxyTargetClass(true);
    return proxyCreator;
  }

  /**
   * ???
   *
   * @param securityManager 自定义的安全管理器
   * @return AuthorizationAttributeSourceAdvisor
   */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor auth = new AuthorizationAttributeSourceAdvisor();
    auth.setSecurityManager(securityManager);
    return auth;
  }

  /**
   * 重写数据域<br/>
   * 即获取用户名、密码、角色、权限的sql<br/>
   *
   * @return 数据域
   */
  @Bean
  public Realm customRealm() {
    return new MyRealm();
  }

  /**
   * 重写默认的会话管理器
   *
   * @return org.apache.shiro.session.mgt.SessionManager
   */
  @Bean
  public DefaultWebSessionManager getMySessionManage() {
    return new MySessionManage();
  }

  /**
   * <pre>
   * 配置redis数据源
   * 1、配置为redisDao的数据源
   * 2、配置为redisCache的数据源
   * </pre>
   *
   * @return redis数据源
   */
  public RedisManager myRedisManager() {
    RedisManager redisManager = new RedisManager();
    redisManager.setHost(redisHost + ":" + redisPort);
    redisManager.setPassword(redisPass);
    return redisManager;
  }

  /**
   * <pre>
   * redis会话dao
   * 1、需要设置redis数据源
   * </pre>
   *
   * @return redis会话dao
   */
  public SessionDAO myRedisSessionDao() {
    RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
    redisSessionDAO.setRedisManager(myRedisManager());
    return redisSessionDAO;
  }


  /**
   * <pre>
   * redis缓存管理器
   * 1、作为session管理器缓存
   * 2、作为安全管理器缓存
   * </pre>
   *
   * @return redis缓存管理器
   */
  public RedisCacheManager myRedisCacheManager() {
    RedisCacheManager redisCacheManager = new RedisCacheManager();
    redisCacheManager.setRedisManager(myRedisManager());
    return redisCacheManager;
  }

  /**
   * <pre>
   * redis版本的回话管理器
   * 1、使用默认的会话管理器
   * 2、需要配置redis缓存管理器
   * 3、需要配置redis会话dao
   * </pre>
   *
   * @return redis版本的回话管理器
   */
  public SessionManager myRedisSessionManager() {
    DefaultSessionManager sessionManager = getMySessionManage();
    sessionManager.setSessionDAO(myRedisSessionDao());
    sessionManager.setCacheManager(myRedisCacheManager());
    return sessionManager;
  }


  /**
   * <pre>
   * 注入安全管理器
   * 1、配置数据域，即用户名、密码、角色、权限的来源
   * 2、测试redis连通性，如果可以连接
   * 2.1、使用redis作为缓存管理器
   * 2.2、使用redis作为会话管理器
   * 3、如果redis无法连通
   * 3.1、使用默认的会话管理器
   * </pre>
   *
   * @return org.apache.shiro.mgt.SecurityManager
   */
  @Bean
  public SecurityManager defaultSecurityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

    // 指定数据域
    securityManager.setRealm(customRealm());

    // 测试redis连通性，如果redis可以连接，则使用redis作为各种缓存
    try (Jedis jedis = new Jedis(redisHost, Integer.parseInt(redisPort))) {
      // 测试redis连通性，如果连接不上，则抛出异常被捕获
      jedis.auth(redisPass);
      log.info("测试redis连接性 {}", jedis.get("hello"));
      securityManager.setCacheManager(myRedisCacheManager());
      securityManager.setSessionManager(myRedisSessionManager());
    } catch (Exception e) {
      // 如果redis无法连接，使用默认的session管理器，即不使用缓存
      log.error("连接到redis失败：不使用缓存，仅使用默认的会话管理器", e);
      securityManager.setSessionManager(getMySessionManage());
    }

    return securityManager;
  }

  /**
   * <pre>
   * shiro过滤器，被注入到filterChain中，接口请求时所有的filter被依次执行
   * 1、指定安全管理器，参见{@link ShiroConfig#defaultSecurityManager()}
   * 2、指定认证失败、授权失败的行为
   * 2.1、authc表示登录过滤器，perms表示权限过滤器
   * 2.2、认证过滤器默认行为：认证失败->跳转到登录接口；现重写认证失败，重写为返回401
   * 2.3、重写授权过滤器：认证失败则返回403
   * 3、指定需要被拦截的url、需要被放行的url
   * 3.1、authc为需要认证、perms为需要授权、anon为不需要认证和授权
   * 3.2、authc，"/**"表示所有接口需要认证
   * 3.2、perms[/shiro-manage/findAllUser]需要指定权限点名称
   * 3.4、权限一般用注解@requirePermission指定，过滤链中设置的权限比注解设置的优先级高
   * 3.5、过滤链顺序执行，所以对于认证：一般先设置全部接口需要认证，然后指定认定接口不需要认证；
   * </pre>
   *
   * @param securityManager 容器中的安全管理器
   * @return shiro过滤器工厂
   */
  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();

    // 指定安全管理器
    filterFactory.setSecurityManager(securityManager);

    // 重写shiro过滤器，认证过滤器、权限过滤器
    filterFactory.getFilters().put("authc", new MyFormAuthenticationFilter());
    filterFactory.getFilters().put("perms", new MyPermissionAuthorizationFilter());

    // 配置需要拦截的url
    Map<String, String> chainMap = new HashMap<>(8);
    filterFactory.setFilterChainDefinitionMap(chainMap);
    // 配置所有接口需要认证，
    chainMap.put("/**", "authc");
    // 配置接口上的授权，大部分接口授权通过注解配置了，通过注解配置的权限点在项目启动时会被扫描到数据库中
    chainMap.put("/hello/helloWithShiroRole", "perms[/hello/helloWithShiroRole]");
    // 放行swagger文档
    chainMap.put("/doc.html", "anon");
    chainMap.put("/webjars/js/chunk*.js", "anon");
    chainMap.put("/swagger-resources/configuration/ui", "anon");
    chainMap.put("/swagger-resources", "anon");
    chainMap.put("/v2/api-docs*", "anon");
    // login、logout不需要认证，不需要授权
    chainMap.put("/shiro-manage/logout", "anon");
    chainMap.put("/shiro-manage/login", "anon");
    chainMap.put("/admin/findMenu", "anon");
    chainMap.put("/hello/helloWithoutShiro", "anon");

    return filterFactory;
  }
}
