package ink.fujisann.learning.stock.repository;

import ink.fujisann.learning.stock.pojo.StockScoreFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockScoreFactorRepository extends JpaRepository<StockScoreFactor, String> {
}
