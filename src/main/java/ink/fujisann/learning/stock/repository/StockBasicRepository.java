package ink.fujisann.learning.stock.repository;

import ink.fujisann.learning.stock.pojo.StockBasic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockBasicRepository extends JpaRepository<StockBasic, String> {
}
