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

}
