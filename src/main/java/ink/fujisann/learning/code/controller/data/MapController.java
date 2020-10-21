package ink.fujisann.learning.code.controller.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ink.fujisann.learning.base.exception.BusinessException.ExceptionBuilder;
import ink.fujisann.learning.code.repository.GeoRepository;
import ink.fujisann.learning.base.utils.common.ApiUrl;
import ink.fujisann.learning.code.vo.data.Geo;
import com.google.common.collect.Iterables;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: 地图接口数据
 * @author: hulei
 * @create: 2020-06-28 20:58:02
 */
@Slf4j
@Api(
    value = "MapController",
    tags = {"003-地图接口数据"})
@RestController // @Rest + @ResponseBody
@RequestMapping("/mapData")
public class MapController {
  @Autowired private GeoRepository geoRepository;

  @ApiOperation(value = "添加单个地区数据", notes = "添加单个地区数据")
  @PostMapping("/addArea")
  public Geo addArea(@RequestBody Geo geo) {
    return geoRepository.save(geo);
  }

  @ApiOperation(value = "批量添加地区数据", notes = "批量添加地区数据")
  @PostMapping("/addAreaBatch")
  public Iterable<Geo> addAreaBatch() {
    long start = System.currentTimeMillis();
    Iterable<Geo> iterable = findAreaByApi();
    Iterable<Geo> result = geoRepository.saveAll(iterable);
    log.info("批量添加 {} 条数据耗时：{} ms", Iterables.size(iterable), System.currentTimeMillis() - start);
    return result;
  }

  @ApiOperation(value = "查询所有地区", notes = "查询所有地区")
  @GetMapping("/findAreaAll")
  public Iterable<Geo> findAreaAll() {
    return geoRepository.findAll();
  }

  @ApiOperation(value = "删除全部地区数据", notes = "删除全部地区数据")
  @DeleteMapping("deleteAll")
  public void deleteAll() {
    geoRepository.deleteAll();
  }

  @Autowired private ApplicationContext applicationContext;

  /**
   * @description 通过定时任务从国家统计局爬取行政区划数据，每次定时任务处理一个省份数据，全部处理完成删除省份列表的redis
   *     通过定时任务的原因是因为连续爬取多个省份的全部数据，会大量报错，可能是被爬取网站设置了防爬策略，后续考虑错误的具体原因
   *     并设置特定的爬虫策略，后面改成通过Timer自己实现可以结束的定时任务
   * @param
   * @return void
   * @author hulei
   * @date 2020-07-04 19:59:03
   */
  // @Scheduled(cron = "0 0/30 * * * ?") // 秒、分、时、日、月、星期、年
  @ApiOperation(value = "通过定时任务处理所有省份数据", notes = "通过定时任务处理所有省份数据, 处理完毕后取消定时任务")
  @GetMapping("/findAllProvinceDataByScheduled")
  public void findAllProvinceDataByScheduled() {
    log.info("处理所有省份数据的定时任务开始");
    Timer timer = new Timer();
    MapController mapController =
        applicationContext.getBean(MapController.class); // 获取容器中的实例，保证所有注入的依赖可用
    timer.schedule(mapController.new ProcessDataTask(timer), 0, 15 * 60 * 1000);
    log.info("处理所有省份数据的定时任务结束");
  }

  public class ProcessDataTask extends TimerTask {
    private Timer timer;

    public ProcessDataTask(Timer timer) {
      this.timer = timer;
    }

    @Override
    public void run() {
      boolean hasProvinceList = redisTemplate.hasKey(province_url_redis_key); // 省列表的key是否被移除了
      if (!hasProvinceList) {
        this.timer.cancel(); // 如果省列表的redis被删除了，取消任务队列(而不是当前任务)
        return;
      }
      processAllData4SingleProvince();
    }
  }

  @ApiOperation(
      value = "处理redis中一个省份下所有的数据",
      notes =
          "处理redis省份列表中一个省份下全部数据，并异步入库，处理后从省份列表中删除一条省份的url。"
              + "处理过程中会在redis中记录一个省份下所有错误的url, 并尝试处理，处理完成删除redis，处理北京全部数据大约耗费5分钟")
  @GetMapping("/processAllData4SingleProvince")
  public String processAllData4SingleProvince() {
    log.info("处理redis中一个省份下所有的数据开始");
    long start = System.currentTimeMillis();
    String provinceUrl = redisTemplate.opsForList().rightPop(province_url_redis_key);
    if (StringUtils.isEmpty(provinceUrl)) {
      log.info("所有省份全部处理完毕");
      redisTemplate.delete(province_url_redis_key); // 数据全部处理后删除此redis
      return "redis中所有省份全部处理完毕，请确认";
    }
    log.info("当前从redis中获取到的省份url为{}", provinceUrl);
    String url = provinceUrl.split("#")[1];
    String currentProvinceCode = url.substring(url.lastIndexOf('/') + 1, url.lastIndexOf('.'));
    findDataAndWriteToDb(currentProvinceCode, provinceUrl); // 爬取主要的数据并异步写入数据库
    processErrorDataFromRedis(currentProvinceCode); // 主要数据处理完，继续处理redis中错误的数据
    log.info("处理redis中一个省份下所有的数据结束, 耗时 {} ms", System.currentTimeMillis() - start);
    return "当前省份下数据全部处理完毕，请确认redis中省份列表中删除了该省份";
  }

  private static final int expect_max_count = 100; // 假定一个省份下最多有一百个错误的url需要处理

  @ApiOperation(value = "处理指定省份下所有错误的url", notes = "根据省份code，处理保存在redis中错误的数据，直到处理完所有数据或者达到最大的遍历次数")
  @GetMapping("/processErrorDataFromRedis")
  public void processErrorDataFromRedis(String provinceCode) {
    int loopCount = 0;
    while (true) {
      loopCount++; // 记录一个省份下错误url的处理次数
      String lastErrorUrl =
          redisTemplate.opsForList().rightPop(error_url_prefix_redis_key + provinceCode);
      if (StringUtils.isEmpty(lastErrorUrl)
          || loopCount >= expect_max_count) { // 一个省份下错误数据全部处理完毕，或者处理次数达到限值，结束循环
        log.info("redis中全部错误数据解析完毕");
        redisTemplate.delete(error_url_prefix_redis_key + provinceCode); // 全部处理完成，删除redis
        return;
      }
      log.info("从redis中读取到的url为 {}", lastErrorUrl);
      findDataAndWriteToDb(provinceCode, lastErrorUrl); // 解析一个url下的数据，解析后异步写入数据库
    }
  }

  // 一次性递归爬取url下所有数据，失败的写入redis，最后将成功的数据异步写入数据库
  private void findDataAndWriteToDb(String provinceCode, String dataUrl) {
    String parentCode = dataUrl.split("#")[0];
    String url = dataUrl.split("#")[1];
    List<Geo> result = findGeoListRecursion(url, parentCode, provinceCode); // 处理错误的数据会重新推入redis中
    executorService.submit(new AsyncInsertToDb(geoRepository, result, url)); // 解析出来的数据异步写入数据库中
  }

  private static final String NBOS_AREA_URL_BASE =
      "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/";
  private static final String province_url_redis_key =
      "learningSql:provinceUrlList:china"; // 存放省份的url
  private static final String error_url_prefix_redis_key =
      "learningSql:errorUrlList:"; // 存放每个身份解析失败的url

  /**
   * @description 省级数据直接入库，并记录url到redis中，以备后续定时任务解析每个省中所有数据
   * @param
   * @return java.util.List<ink.fujisann.learning.code.vo.data.Geo>
   * @author hulei
   * @date 2020-07-04 00:37:32
   */
  @ApiOperation(value = "解析国家统计局地理数据的入口", notes = "省级数据直接入库，并记录url到redis中，" + "以备后续定时任务解析每个省中所有数据")
  @GetMapping("/writeProvinceDataAndRecordProvince")
  public String writeProvinceDataAndRecordProvince() {
    boolean redisHasKey = redisTemplate.hasKey(province_url_redis_key);
    boolean geoDataExist = geoRepository.count() != 0;
    if (redisHasKey || geoDataExist) {
      return "redis中或者geo_t表中有数据，请删除后重新解析省份数据";
    }
    log.info("开始爬取省份数据");
    String url = NBOS_AREA_URL_BASE + "index.html";
    String provinceParentCode = "000000000000";
    String content = findAreaContentByUrl(url, provinceParentCode, "00"); // 从指定网址获取html文件
    if (StringUtils.isEmpty(content)) {
      log.info("获取省份页面失败，退出");
      throw new ExceptionBuilder().setCode("000").setMsg("获取省份页面失败，稍后请重试").build();
    }
    Document document = Jsoup.parse(content); // 解析html字符串
    Elements elements = document.select("tr.provincetr a"); // class选择器
    elements.stream()
        .anyMatch( // 如果return true，表示找到一个匹配项，则结束forEach，并返回true
            e -> {
              long start = System.currentTimeMillis();
              String href = e.attr("href"); // 65.html
              String id = href.substring(0, href.indexOf('.')); // 65
              String areaCode = id.concat("0000000000");
              String provinceName = e.text();
              Geo tmp =
                  Geo.builder()
                      .parentCode(provinceParentCode)
                      .areaType(String.valueOf(id.length()))
                      .areaCode(areaCode)
                      .areaName(provinceName)
                      .build(); // 内部类构建的对象没有@Entity标记，会报错
              List<Geo> result = new ArrayList<>();
              result.add(tmp);
              String recursionUrl = url.substring(0, url.lastIndexOf('/')) + "/" + href;
              redisTemplate
                  .opsForList()
                  .leftPush(province_url_redis_key, areaCode + "#" + recursionUrl);
              log.info("遍历了省份{}, 耗时 {} ms", provinceName, System.currentTimeMillis() - start);
              executorService.submit(new MapController.AsyncInsertToDb(geoRepository, result, url));
              return false; // 返回false，foreach会继续遍历下一个
            });
    return "省份数据解析完毕";
  }

  // 异步写入数据库
  public static class AsyncInsertToDb implements Runnable {
    private List<Geo> geoList;
    private GeoRepository geoRepository;
    private String url;

    public AsyncInsertToDb(GeoRepository geoRepository, List<Geo> geoList, String url) {
      this.geoList = geoList;
      this.geoRepository = geoRepository;
      this.url = url;
    }

    @Transactional
    @Override
    public void run() {
      log.info("开始写数据库 {}", JSONArray.toJSONString(geoList));
      long start = System.currentTimeMillis();
      try {
        geoRepository.saveAll(geoList);
      } catch (Exception e) {
        log.error("写入数据库异常", e);
      }
      log.info(
          "写入数据库完成, 数据来自url {}, 写入数据库记录数 {} 条， 耗时 {} ms",
          url,
          geoList.size(),
          System.currentTimeMillis() - start);
    }
  }

  // 定义线程池
  private static ExecutorService executorService = Executors.newCachedThreadPool();
  @Autowired private RedisTemplate<String, String> redisTemplate;

  private List<Geo> findGeoListRecursion(String url, String parentCode, final String provinceCode) {
    String content = findAreaContentByUrl(url, parentCode, provinceCode);
    if (StringUtils.isEmpty(content)) { // 异常返回空集合
      return new ArrayList<>();
    }
    Document document = Jsoup.parse(content);
    Elements elements =
        document.select("tr[class$=tr]"); // class结尾是tr的，所有tr标签，例如<tr class='citytr'>
    List<Geo> result = new ArrayList<>();
    elements.forEach(
        e -> {
          Elements elementsTmp = e.select("a");
          Geo tmp = new Geo();
          tmp.setParentCode(parentCode);
          if (!CollectionUtils.isEmpty(elementsTmp)) { // 如果有a标签，按照a标签解析
            // <tr class='citytr'>
            //     <td><a href='65/6501.html'>650100000000</a></td>
            //     <td><a href='65/6501.html'>乌鲁木齐市</a></td>
            // </tr>
            long start = System.currentTimeMillis();
            Element elementA = elementsTmp.get(0);
            Element elementB = elementsTmp.get(1);
            String href = elementA.attr("href"); // 65/6501.html
            String id = href.substring(href.lastIndexOf('/') + 1, href.indexOf('.')); // 6501
            String code = elementA.text(); // 650100000000
            String name = elementB.text(); // 乌鲁木齐市
            tmp.setAreaType(String.valueOf(id.length()));
            tmp.setAreaCode(code);
            tmp.setAreaName(name);
            result.add(tmp);
            String recursionUrl = url.substring(0, url.lastIndexOf('/')) + "/" + href;
            List<Geo> recursionResult =
                findGeoListRecursion(recursionUrl, code, provinceCode); // 如果当前层级有a标签，需要递归查询
            result.addAll(recursionResult);
            log.info("遍历 {}, 耗时 {} ms", name, System.currentTimeMillis() - start);
          } else { // 否则按照tr标签解析
            // <tr class='villagetr'>
            //     <td>650103004002</td>
            //     <td>111</td>
            //     <td>明园有色社区居委会</td>
            // </tr>
            long start = System.currentTimeMillis();
            elementsTmp = e.select("td");
            String code = elementsTmp.get(0).text(); // 第一个td
            String name = elementsTmp.get(elementsTmp.size() - 1).text(); // 最后一个td
            tmp.setAreaCode(code);
            tmp.setAreaName(name);
            tmp.setAreaType(String.valueOf(code.length()));
            result.add(tmp);
            log.info("遍历 {}, 耗时 {} ms", name, System.currentTimeMillis() - start);
          }
        });
    log.info("当前抓取到的数据集合{}", JSONArray.toJSONString(result));
    return result;
  }

  private String findAreaContentByUrl(String url, String parentCode, String provinceCode) {
    HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory =
        new HttpComponentsClientHttpRequestFactory();
    httpComponentsClientHttpRequestFactory.setConnectTimeout(300);
    httpComponentsClientHttpRequestFactory.setReadTimeout(300);
    RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);
    restTemplate
        .getMessageConverters()
        .set(1, new StringHttpMessageConverter(Charset.forName("gb2312")));
    log.info("调用统计局url：{}", url);
    ResponseEntity<String> responseEntity = null;
    try {
      long start = System.currentTimeMillis();
      responseEntity = restTemplate.getForEntity(url, String.class);
      log.info("当前耗时：{} ms，url: {}", System.currentTimeMillis() - start, url);
    } catch (RestClientException e) {
      log.info("异常的url: {}, parentCode: {}", url, parentCode);
      redisTemplate
          .opsForList()
          .leftPush(
              error_url_prefix_redis_key + provinceCode,
              parentCode + "#" + url); // 每个省份失败的url，存到redis的一个list中
      log.error("异常信息", e);
    }
    if (responseEntity == null) { // 异常返回null
      return null;
    }
    return responseEntity.getBody();
  }

  private Iterable<Geo> findAreaByApi() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(ApiUrl.area.getValue(), String.class);
    JSONObject jsonObject = JSON.parseObject(responseEntity.getBody()).getJSONObject("content");
    ArrayList<Geo> arrayList = new ArrayList<>();
    handleAreaData(jsonObject, arrayList, 0);
    return arrayList;
  }

  private List<Geo> handleAreaData(
      JSONObject jsonObject, ArrayList<Geo> arrayList, Integer parentId) {
    Geo geo = new Geo();
    geo.setParentCode(String.valueOf(parentId));
    Integer recursionParentId =
        Integer.parseInt(jsonObject.getString("area_code")); // 当前层级的areaCode，即递归方法中的parentId
    geo.setAreaCode(String.valueOf(recursionParentId));
    geo.setAreaName(jsonObject.getString("area_name"));
    geo.setAreaType(jsonObject.getString("area_type"));
    geo.setGeo(jsonObject.getString("geo"));
    arrayList.add(geo);

    JSONArray arrayRecursion = jsonObject.getJSONArray("sub");
    if (!CollectionUtils.isEmpty(arrayRecursion)) {
      arrayRecursion.stream()
          .forEach(
              e -> {
                handleAreaData((JSONObject) e, arrayList, recursionParentId);
              });
    }
    return arrayList;
  }
}
