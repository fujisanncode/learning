package ink.fujisann.learning.stock.controller;

import ink.fujisann.learning.stock.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

@RequestMapping("/stock")
@RestController
@Slf4j
@Api(tags = {"股票数据入库"})
public class StockController {

    private StockService stockService;

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @SneakyThrows
    @ApiOperation("构建数据")
    @GetMapping("/build")
    public void build(@RequestParam String methodName) {
        Class<StockService> aClass = StockService.class;
        Method method = aClass.getDeclaredMethod(methodName);
        method.invoke(stockService);
    }

    @ApiOperation("插入指定日期的数据")
    @GetMapping("/tradeDate")
    public void tradeDate(@RequestParam String date) {
        stockService.todayTrade(date);
    }

    @ApiOperation("查询新股票")
    @GetMapping("/addNewStock")
    public void addNewStock() {
        stockService.addNewStock();
    }

    @ApiOperation("添加假期")
    @GetMapping("/addHoliday")
    public void addHoliday() {
        stockService.addHoliday();
    }
}
