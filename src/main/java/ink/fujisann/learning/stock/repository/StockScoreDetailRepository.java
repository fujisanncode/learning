package ink.fujisann.learning.stock.repository;

import ink.fujisann.learning.stock.pojo.StockScoreDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockScoreDetailRepository extends JpaRepository<StockScoreDetail, String> {
}
