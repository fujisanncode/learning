package ink.fujisann.learning.testService;

import ink.fujisann.learning.LearningApplication;
import ink.fujisann.learning.stock.service.StockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@TestPropertySource("classpath:bootstrap.properties")
@SpringBootTest(classes = LearningApplication.class)
@RunWith(SpringRunner.class)
public class StockTest {
    private StockService stockService;

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @Test
    public void testRest() {
        //stockService.stockBasic();
    }
}
