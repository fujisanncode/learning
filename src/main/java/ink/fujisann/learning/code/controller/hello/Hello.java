package ink.fujisann.learning.code.controller.hello;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ink.fujisann.learning.base.designPattern.observer.springlistener.UserRegisterService;
import ink.fujisann.learning.base.utils.common.SpringContextHolder;
import ink.fujisann.learning.code.dao.CustomerMapper;
import ink.fujisann.learning.code.dao.RegionLv1Mapper;
import ink.fujisann.learning.code.req.PageReq;
import ink.fujisann.learning.code.mybatis.RegionLv1;
import ink.fujisann.learning.code.pojo.Plan;
import ink.fujisann.learning.code.repository.GeoRepository;
import ink.fujisann.learning.code.resp.CustomerResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/hello")
@Slf4j
@Api(tags = {"000-测试接口"})
public class Hello {

  @Autowired private RegionLv1Mapper regionLv1Mapper;

  @Autowired private ink.fujisann.learning.base.designPattern.observer.listener.UserRegisterService userRegisterService;

  @Autowired
  private UserRegisterService
      springRegisterService;

  @Autowired private GeoRepository geoRepository;

  @Autowired private RedisTemplate<String, String> redisTemplate;

  @ApiOperation(value = "测试timer", notes = "测试timer")
  @GetMapping("/testSchedule")
  public String testSchedule() {
    int count = 0;
    Timer timer = new Timer();
    timer.schedule(new task(count, timer), 0, 3 * 1000);
    Lock lock = new ReentrantLock();
    lock.lock();
    return "定时任务执行结束";
  }

  private static class task extends TimerTask {
    private Integer count;
    private Timer timer;

    public task(Integer count, Timer timer) {
      this.count = count;
      this.timer = timer;
    }

    @Override
    public void run() {
      if (count >= 10) {
        this.timer.cancel(); // 取消任务队列
      }
      log.info("执行定时任务 {} 次", ++count);
    }
  }

  @ApiOperation(value = "测试redisTemplate")
  @GetMapping("/redisTemplateDemo")
  public void redisTemplateDemo() {
    // Geo geo = new Geo();
    // geo.setAreaCode("");
    // geo.setAreaName("");
    // geo.setAreaType("");
    // geo.setParentCode("");
    // geo.setId("");
    // geo.setGeo("");
    // List<Geo> list = new ArrayList();
    // list.add(geo);
    // try {
    //   geoRepository.saveAll(list);
    //   log.info("写入完成");
    // } catch (Exception e) {
    //   log.error("写入异常", e);
    // }
    redisTemplate.opsForValue().set("redis:area:url", "test");
    // redisTemplate.opsForList().leftPush("redis:area:urlL", "test");
    // redisTemplate.opsForList().leftPush("redis:area:url", "test");
  }

  public static void readProperties() throws Exception {
    Properties properties =
        PropertiesLoaderUtils.loadProperties(
            SpringContextHolder.getApplicationContext()
                .getResource("classpath:application-dev1.properties"));
  }

  @PostMapping("/say-hello")
  @ApiOperation("say-hello")
  public String sayHello(@RequestBody Plan plan, PageReq page) throws Exception {
    readProperties();
    log.info("hello world");
    userRegisterService.register();
    springRegisterService.register();
    return "hello world";
  }

  private ApplicationContext context;

  @Autowired
  public void setContext(ApplicationContext context) {
    this.context = context;
  }

  private CustomerMapper customerMapper;

  @Autowired
  public void setCustomerMapper(CustomerMapper customerMapper) {
    this.customerMapper = customerMapper;
  }

  @SneakyThrows
  @ApiOperation("无shiro权限控制")
  @GetMapping("/helloWithoutShiro")
  public PageInfo<CustomerResp> helloWithoutShiro() {
    PageHelper.startPage(1, 10);
    List<CustomerResp> resp = customerMapper.listCustomer();
    return new PageInfo<>(resp);
  }

  @ApiOperation("测试shiro角色权限")
  @GetMapping("/helloWithShiroRole")
  @RequiresPermissions(value = {"/hello/helloWithShiroRole"})
  public void helloWithShiroRole() {

  }

  @PostMapping("/insert-region")
  @ApiOperation("insert-region")
  public void insertRegion(@RequestBody RegionLv1 regionLv1) {
    log.info("insert region -> {}", regionLv1);
    regionLv1.setUpdateTime(new Date());
    try {
      regionLv1Mapper.lockTable();
      RegionLv1 regionLv11Rt = regionLv1Mapper.selectByRegion(regionLv1);
      if (regionLv11Rt == null) {
        log.info("insert region ...");
        regionLv1Mapper.insert(regionLv1);
      }
    } catch (Exception e) {
      log.error("insert region error:", e);
    } finally {
      regionLv1Mapper.unLockTable();
    }
  }
}
