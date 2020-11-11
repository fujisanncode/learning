package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.data.Geo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * 区划
 *
 * @author hulei
 * @date 2020/11/11
 */
public interface GeoRepository extends JpaRepository<Geo, String> {
}
