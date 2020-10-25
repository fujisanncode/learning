package ink.fujisann.learning.code.service;

import ink.fujisann.learning.code.pojo.PageReq;
import ink.fujisann.learning.code.pojo.shelf.Book;
import ink.fujisann.learning.code.pojo.shelf.Web;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 书架
 *
 * @author hulei
 * @date 2020-10-23 22:53:27
 */
public interface ShelfService {
    /**
     * 上传图书
     *
     * @param file 文件流
     */
    void uploadBook(MultipartFile file);

    /**
     * 查询图书列表
     *
     * @return 图书列表
     */
    List<Book> listBook();

    /**
     * 新增网站
     *
     * @param web 新增请求体
     */
    void addWeb(Web web);

    /**
     * 分页查询网站列表
     *
     * @param pageReq 分页参数
     * @return 网站分页
     */
    Page<Web> pageWeb(PageReq pageReq);
}
