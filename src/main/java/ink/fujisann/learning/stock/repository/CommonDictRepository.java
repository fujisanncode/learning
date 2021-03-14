package ink.fujisann.learning.stock.repository;

import ink.fujisann.learning.stock.pojo.CommonDict;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonDictRepository extends JpaRepository<CommonDict, String> {
}
