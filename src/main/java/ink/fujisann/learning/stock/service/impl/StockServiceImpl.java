package ink.fujisann.learning.stock.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ink.fujisann.learning.base.utils.common.ReadUtil;
import ink.fujisann.learning.stock.pojo.Daily;
import ink.fujisann.learning.stock.pojo.Holiday;
import ink.fujisann.learning.stock.pojo.StockBasic;
import ink.fujisann.learning.stock.repository.DailyRepository;
import ink.fujisann.learning.stock.repository.HolidayRepository;
import ink.fujisann.learning.stock.repository.StockBasicRepository;
import ink.fujisann.learning.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class StockServiceImpl implements StockService {
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private StockBasicRepository stockBasicRepository;

    @Autowired
    public void setStockBasicRepository(StockBasicRepository stockBasicRepository) {
        this.stockBasicRepository = stockBasicRepository;
    }

    @Override
    public void stockBasic() {
        ArrayList<StockBasic> insertBatch = buildStockBasic();

        // 批量寫數據
        stockBasicRepository.saveAll(insertBatch);
    }

    @NotNull
    private ArrayList<StockBasic> buildStockBasic() {
        // 构造请求参数
        String url = "http://api.waditu.com";
        JSONObject body = new JSONObject();
        body.put("api_name", "stock_basic");
        body.put("token", "fc68e0b8446064e8b60576701abcf9644fd776b74eedf43f906c546c");
        JSONObject params = new JSONObject();
        params.put("list_stauts", "L");
        body.put("params", params);
        body.put("fields", "ts_code,name,area,industry,list_date");

        // 调用tushare.pro接口获取股票数据
        JSONArray jsonArray = getObjects(url, body);
        ArrayList<StockBasic> insertBatch = new ArrayList<>();
        jsonArray.forEach(o -> {
            List o1 = (List) o;
            StockBasic stockBasic = new StockBasic();
            insertBatch.add(stockBasic);
            stockBasic.setTsCode((String) o1.get(0));
            stockBasic.setName((String) o1.get(1));
            stockBasic.setArea((String) o1.get(2));
            stockBasic.setIndustry((String) o1.get(3));
            stockBasic.setListDate(DateUtil.parse((String) o1.get(4), "yyyyMMdd"));
        });
        return insertBatch;
    }

    private DailyRepository dailyRepository;

    @Autowired
    public void setDailyRepository(DailyRepository dailyRepository) {
        this.dailyRepository = dailyRepository;
    }

    @Override
    public void daily() {
        List<StockBasic> all = stockBasicRepository.findAll();
        List<String> exist = Stream.of("000009.SZ").collect(Collectors.toList());
        Stream<StockBasic> notExist = all.stream().filter(stockBasic -> !exist.contains(stockBasic.getTsCode()));
        notExist.forEach(stockBasic -> {
            dailyByTsCode(stockBasic.getTsCode(), null);
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void todayTrade(String date) {
        addNewStock();
        addToday(date);
    }

    private void dailyByTsCode(String tsCode, String tradeDate) {
        // 构造请求参数
        String url = "http://api.waditu.com";
        JSONObject body = new JSONObject();
        body.put("api_name", "daily");
        body.put("token", "fc68e0b8446064e8b60576701abcf9644fd776b74eedf43f906c546c");
        JSONObject params = new JSONObject();
        params.put("ts_code", tsCode);

        if (StringUtils.isEmpty(tradeDate)) {
            // 按时间段查询
            params.put("start_date", "20180309");
            params.put("end_date", "20210309");
        } else {
            // 按交易时间查询
            params.put("trade_date", tradeDate);
        }

        body.put("params", params);
        body.put("fields", "ts_code,trade_date,open,high,low,close,pre_close,change,pct_chg,vol,amount");
        JSONArray jsonArray = getObjects(url, body);

        List<Daily> insertBatch = new ArrayList<>();
        jsonArray.forEach(o -> {
            List o1 = (List) o;
            Daily daily = new Daily();
            insertBatch.add(daily);
            daily.setTsCode((String) o1.get(0));
            daily.setTradeDate(DateUtil.parse((String) o1.get(1), "yyyyMMdd"));
            daily.setOpen(BigDecimal.valueOf((Double) o1.get(2)));
            daily.setHigh(BigDecimal.valueOf((Double) o1.get(3)));
            daily.setLow(BigDecimal.valueOf((Double) o1.get(4)));
            daily.setClose(BigDecimal.valueOf((Double) o1.get(5)));

            daily.setPreClose(BigDecimal.valueOf((Double) o1.get(6)));
            daily.setChange(BigDecimal.valueOf((Double) o1.get(7)));
            daily.setPctChg(BigDecimal.valueOf((Double) o1.get(8)));
            daily.setVol(BigDecimal.valueOf((Double) o1.get(9)));
            daily.setAmount(BigDecimal.valueOf((Double) o1.get(10)));
        });

        // 批量寫數據
        dailyRepository.saveAll(insertBatch);
        log.info("{} 数据插入完毕 {} 行", tsCode, insertBatch.size());
    }

    private JSONArray getObjects(String url, JSONObject body) {
        // 调用tushare.pro接口获取股票数据
        HttpEntity<JSONObject> entity = new HttpEntity<>(body, new HttpHeaders());
        ResponseEntity<JSONObject> postForEntity = restTemplate.postForEntity(url, entity, JSONObject.class);

        // 解析rest結果
        return Optional.ofNullable(postForEntity.getBody())
                .orElseThrow(RuntimeException::new)
                .getJSONObject("data")
                .getJSONArray("items");
    }

    /**
     * tsCode最大数量为100
     */
    public static final int ts_code_max = 100;

    private void addToday(String date) {
        // 如果没有传入date，则使用当前时间
        String tradeDate = Optional.ofNullable(date)
                .orElse(DateUtil.format(new Date(), "yyyy-MM-dd"));

        List<StockBasic> all = stockBasicRepository.findAll();

        // tsCode最大100个，多次调用接口完成当日所有股票价格的查询
        int listLen = all.size();
        int outLoop = listLen / ts_code_max;
        for (int i = 0; i < outLoop + 1; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < ts_code_max; j++) {
                int index = i * 100 + j;
                // 如果超出list长度，则结束遍历
                if (index >= listLen) {
                    break;
                }
                StockBasic stockBasic = all.get(index);
                builder.append(stockBasic.getTsCode()).append(",");
            }
            String substring = builder.substring(0, builder.length() - 1);
            dailyByTsCode(substring, tradeDate);
        }
    }

    @Override
    public void addNewStock() {
        ArrayList<StockBasic> stockBasics = buildStockBasic();
        List<StockBasic> all = stockBasicRepository.findAll();
        List<StockBasic> list = new ArrayList<>();
        for (StockBasic stockBasic : stockBasics) {
            // 如果all-数据库中找不到匹配项，则需要加入数据库
            boolean noneMatch = all.stream()
                    .noneMatch(stockBasic1 -> stockBasic1.getTsCode().equals(stockBasic.getTsCode()));
            if (noneMatch) {
                list.add(stockBasic);
            }
        }

        stockBasicRepository.saveAll(list);
    }

    private HolidayRepository holidayRepository;

    @Autowired
    public void setHolidayRepository(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    @Override
    public void addHoliday() {
        String str = ReadUtil.read("json/holiday.json");
        JSONObject jsonObject = JSONObject.parseObject(str);
        JSONObject data = jsonObject.getJSONObject("data");
        Set<String> keySet = data.keySet();
        List<Holiday> insert = new ArrayList<>();
        for (String s : keySet) {
            JSONObject object = data.getJSONObject(s);
            Set<String> strings = object.keySet();
            for (String string : strings) {
                JSONObject object1 = object.getJSONObject(string);
                String name = object1.getString("name");
                String dateStr = object1.getString("date");
                Holiday holiday = new Holiday();
                insert.add(holiday);
                holiday.setName(name);
                holiday.setDate(DateUtil.parse(dateStr, "yyyy-MM-dd"));
            }

        }
        holidayRepository.saveAll(insert);
    }


}
