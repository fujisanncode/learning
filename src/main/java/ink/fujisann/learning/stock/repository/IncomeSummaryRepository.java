package ink.fujisann.learning.stock.repository;

import ink.fujisann.learning.stock.pojo.IncomeSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeSummaryRepository extends JpaRepository<IncomeSummary, String> {
}
