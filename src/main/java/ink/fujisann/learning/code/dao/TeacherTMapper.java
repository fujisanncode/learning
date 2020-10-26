package ink.fujisann.learning.code.dao;

import ink.fujisann.learning.code.pojo.mybatis.ArticleT;
import ink.fujisann.learning.code.pojo.mybatis.StuTeaT;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TeacherTMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_t
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String articleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_t
     *
     * @mbggenerated
     */
    int insert(ArticleT record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_t
     *
     * @mbggenerated
     */
    ArticleT selectByPrimaryKey(String articleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_t
     *
     * @mbggenerated
     */
    List<ArticleT> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table article_t
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ArticleT record);

    List<StuTeaT> selectStuTea(List<String> students);
}