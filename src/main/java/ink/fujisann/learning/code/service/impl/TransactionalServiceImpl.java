package ink.fujisann.learning.code.service.impl;

import ink.fujisann.learning.code.dao.ArticleTMapper;
import ink.fujisann.learning.code.pojo.mybatis.ArticleT;
import ink.fujisann.learning.code.service.ITransactionalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TransactionalServiceImpl implements ITransactionalService {

    // 文章表dao
    @Autowired
    private ArticleTMapper articleTMapper;

    // Isolation 事务隔离级别 mysql 默认事务级别可重复读(锁定行记录，不能防止幻读) 默认DEFAULT
    // propagation 事务传播特性 required(不存在事务的情况下，开启新的事务) 默认REQUIRED
    // rollbackfor 如果异常没有捕捉，则异常前对数据库的更新操作会回滚(如果捕捉了异常，则不会回滚)
    @Override
    @Transactional (isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean updateArticle(ArticleT articleT) {
        log.info("updateArticle =====> get in");
        int rt = articleTMapper.updateByPrimaryKey(articleT);
        int i = 0;
        try {
            int j = 10 / i;
        } catch (Exception e) {
            // 堆栈对象必须放到最后一个参数，字符串参数需要用{}占位符
            log.error("updateArticle {}", "lalala", e);
        }
        return rt > 0;
        // throw new BusinessException();
    }
}
