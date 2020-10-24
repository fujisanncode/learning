package ink.fujisann.learning.code.service;

import ink.fujisann.learning.code.pojo.mybatis.ArticleT;

public interface ITransactionalService {

    public Boolean updateArticle(ArticleT articleT);

}
