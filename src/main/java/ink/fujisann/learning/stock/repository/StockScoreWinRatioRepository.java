package ink.fujisann.learning.stock.repository;

import ink.fujisann.learning.stock.pojo.StockScoreWinRatio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockScoreWinRatioRepository extends JpaRepository<StockScoreWinRatio, String> {
}
