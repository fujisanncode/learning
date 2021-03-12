package ink.fujisann.learning.stock.repository;

import ink.fujisann.learning.stock.pojo.StockDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyRepository extends JpaRepository<StockDaily, String> {
}
