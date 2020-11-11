package ink.fujisann.learning.code.service;

import ink.fujisann.learning.code.pojo.MongoBlog;

import java.util.List;

/**
 * 博客
 *
 * @author hulei
 * @date 2020-11-10 23:21:23:21
 */
public interface BlogService {

    /**
     * 保存博客
     *
     * @param mongoBlog 博客
     */
    String save(MongoBlog mongoBlog);

    /**
     * 查询所有博客
     *
     * @return 博客列表
     */
    List<MongoBlog> findAll();

    /**
     * 按id查询博客
     *
     * @param id 博客id
     * @return 博客
     */
    MongoBlog findById(String id);

    /**
     * 更新博客
     *
     * @param mongoBlog 更新的请求体
     */
    void update(MongoBlog mongoBlog);
}
