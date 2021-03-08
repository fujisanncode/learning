package ink.fujisann.learning.stock.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ink.fujisann.learning.stock.pojo.Daily;
import ink.fujisann.learning.stock.pojo.StockBasic;
import ink.fujisann.learning.stock.repository.DailyRepository;
import ink.fujisann.learning.stock.repository.StockBasicRepository;
import ink.fujisann.learning.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        HttpEntity<JSONObject> entity = new HttpEntity<>(body, new HttpHeaders());
        ResponseEntity<JSONObject> postForEntity = restTemplate.postForEntity(url, entity, JSONObject.class);

        // 解析rest結果
        JSONArray jsonArray = Optional.ofNullable(postForEntity.getBody())
                .orElseThrow(RuntimeException::new)
                .getJSONObject("data")
                .getJSONArray("items");
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

        // 批量寫數據
        stockBasicRepository.saveAll(insertBatch);
    }

    private DailyRepository dailyRepository;

    @Autowired
    public void setDailyRepository(DailyRepository dailyRepository) {
        this.dailyRepository = dailyRepository;
    }

    @Override
    public void daily() {
        // 构造请求参数
        String url = "http://api.waditu.com";
        JSONObject body = new JSONObject();
        body.put("api_name", "daily");
        body.put("token", "fc68e0b8446064e8b60576701abcf9644fd776b74eedf43f906c546c");
        JSONObject params = new JSONObject();
        params.put("ts_code", "000009.SZ");
        params.put("start_date", "20180309");
        params.put("end_date", "20210309");
        body.put("params", params);
        body.put("fields", "ts_code,trade_date,open,high,low,close,pre_close,change,pct_chg,vol,amount");

        // 调用tushare.pro接口获取股票数据
        HttpEntity<JSONObject> entity = new HttpEntity<>(body, new HttpHeaders());
        ResponseEntity<JSONObject> postForEntity = restTemplate.postForEntity(url, entity, JSONObject.class);

        // 解析rest結果
        JSONArray jsonArray = Optional.ofNullable(postForEntity.getBody())
                .orElseThrow(RuntimeException::new)
                .getJSONObject("data")
                .getJSONArray("items");
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
    }
}
