package ink.fujisann.learning.stock.repository;

import ink.fujisann.learning.stock.pojo.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, String> {
}
