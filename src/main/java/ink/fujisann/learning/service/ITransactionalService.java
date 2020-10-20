package ink.fujisann.learning.service;

import ink.fujisann.learning.vo.mybatis.ArticleT;

public interface ITransactionalService {

    public Boolean updateArticle(ArticleT articleT);

}
