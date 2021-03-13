package ink.fujisann.learning.stock.repository;

import ink.fujisann.learning.stock.pojo.StockScoreTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockScoreTotalRepository extends JpaRepository<StockScoreTotal, String> {
}
