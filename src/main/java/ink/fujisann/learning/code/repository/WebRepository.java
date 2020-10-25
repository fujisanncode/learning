package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.shelf.Web;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 网站数据访问层
 *
 * @author hulei
 * @date 2020-10-24 18:12:18:12
 */
@Repository
public interface WebRepository extends JpaRepository<Web, String>, JpaSpecificationExecutor<Web> {

}
