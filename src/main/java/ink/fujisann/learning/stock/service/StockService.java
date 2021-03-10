package ink.fujisann.learning.stock.service;

public interface StockService {
    /**
     * 股票入库：初始化调用
     */
    void stockBasic();

    /**
     * 历史日k行情入库：初始化调用
     */
    void daily();

    /**
     * 当日交易数据入库
     */
    void todayTrade(String date);

    /**
     * 插入新股票
     */
    void addNewStock();

    void addHoliday();
}
