package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.Geo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 区划
 *
 * @author hulei
 * @date 2020/11/11
 */
public interface GeoRepository extends JpaRepository<Geo, String> {
}
