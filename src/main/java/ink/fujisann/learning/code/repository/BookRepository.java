package ink.fujisann.learning.code.repository;

import ink.fujisann.learning.code.pojo.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 图书数据访问层
 *
 * @author hulei
 * @date 2020-10-24 18:12:18:12
 */
@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    /**
     * 查找用户的书籍列表
     *
     * @param userId 用户id
     * @return 书籍列表
     */
    List<Book> findBooksByUserId(String userId);
}
