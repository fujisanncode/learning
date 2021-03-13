package ink.fujisann.learning.stock.repository;

import ink.fujisann.learning.stock.pojo.StockHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHolidayRepository extends JpaRepository<StockHoliday, String> {
}
