package ink.fujisann.learning.stock.job.tushare;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import ink.fujisann.learning.stock.pojo.StockHoliday;
import ink.fujisann.learning.stock.repository.StockHolidayRepository;
import ink.fujisann.learning.stock.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class StockScheduler {

    private StockService stockService;

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    private StockHolidayRepository stockHolidayRepository;

    @Autowired
    public void setHolidayRepository(StockHolidayRepository stockHolidayRepository) {
        this.stockHolidayRepository = stockHolidayRepository;
    }

    /**
     * 每天15点30分执行一次
     */
    @Scheduled(cron = "0 30 15 * * ?")
    public void todayInsert() {
        // 如果周六周日不执行
        Week week = DateUtil.dayOfWeekEnum(new Date());
        if (Week.SATURDAY.equals(week) || Week.SUNDAY.equals(week)) {
            log.info("今天是周末，不开盘");
            return;
        }

        // 非开盘日不执行
        StockHoliday stockHoliday = new StockHoliday();
        stockHoliday.setDate(new Date());
        Example<StockHoliday> example = Example.of(stockHoliday);
        if (stockHolidayRepository.findOne(example).isPresent()) {
            log.info("今天是节假日，不开盘");
            return;
        }

        // 当日新股, 日k入库
        stockService.todayTrade(null);
    }
}
