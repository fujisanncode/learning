package ink.fujisann.learning.code.controller.article;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ink.fujisann.learning.base.exception.BusinessException.Builder;
import ink.fujisann.learning.base.exception.BusinessExceptionEnum;
import ink.fujisann.learning.code.dao.ArticleTMapper;
import ink.fujisann.learning.code.pojo.mybatis.ArticleT;
import ink.fujisann.learning.code.service.ITransactionalService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping ("/article-manage")
@Api (value = "ArticleManageImpl", tags = "文章表相关接口")
public class ArticleManage {

    @Autowired
    private ArticleTMapper articleTMapper;

    @Autowired
    private ITransactionalService transactionalService;

    @GetMapping ("/query-articles/{curPage}/{pageSize}")
    @ApiOperation (value = "查询文章(分页)", notes = "查询文章(单表查询)，可以传入分页参数", protocols = "http")
    @ApiResponses ({
        @ApiResponse (code = 200, message = "查询文章成功"),
        @ApiResponse (code = 400, message = "参数错误"),
        @ApiResponse (code = 500, message = "服务器错误")
    })
    @ApiImplicitParams ({
        @ApiImplicitParam (paramType = "path", name = "curPage", value = "当前页", defaultValue = "1", required = true, dataType = "int"),
        @ApiImplicitParam (paramType = "path", name = "pageSize", value = "页容量", defaultValue = "10", required = true, dataType = "int")
    })
    public PageInfo<ArticleT> queryArticles(@PathVariable Integer curPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(curPage, pageSize);
        List<ArticleT> articles = articleTMapper.selectAll();
        PageInfo<ArticleT> pageArticles = new PageInfo<>(articles);
        return pageArticles;
    }

    @ApiOperation (value = "插入", notes = "插入一篇文章", produces = "application/json", consumes = "application/json")
    @PostMapping ("/insert-article")
    public Integer insertArticle(@RequestBody ArticleT article) {
        Integer insertRt = null;
        try {
            article.setArticleId(UUID.randomUUID().toString().replace("-", ""));
            article.setArticleUpdateTime(new Date());
            log.info("insert===={}", article.toString());
            insertRt = articleTMapper.insert(article);
        } catch (Exception e) {
            log.info("insert article {}", e);
        }
        return insertRt;
    }

    @ApiOperation (value = "更新", notes = "根据主键更新文章")
    @PostMapping ("/update-article")
    // @Transactional (isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean updateArticle(@RequestBody ArticleT article) {
        // 子方法中更新tag2
        Boolean rt = transactionalService.updateArticle(article);
        // 父方法中更新tag1
        ArticleT articleTNew = new ArticleT();
        articleTNew.setArticleId(article.getArticleId());
        articleTNew.setArticleTag1(article.getArticleTag2());
        articleTMapper.updateByPrimaryKey(articleTNew);
        if (!rt) {
            throw new Builder()
                    .code(BusinessExceptionEnum.EXCEPTION_SQL.getCode())
                    .msg(BusinessExceptionEnum.EXCEPTION_SQL.getMsg())
                    .build();
        }
        return rt;
    }

    @ApiOperation (value = "删除", notes = "根据主键删除文章(软删除)")
    @GetMapping ("/delete-article/{id}")
    public Integer deleteArticle(@PathVariable String id) {
        // 软删除 仅更新状态字段
        ArticleT articleT = new ArticleT();
        articleT.setArticleId(id);
        articleT.setArticleStatus(0);
        Integer rt = articleTMapper.updateByPrimaryKey(articleT);
        return rt;
    }
}
