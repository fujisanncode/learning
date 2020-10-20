package com.example.sqllearning.service;

import com.example.sqllearning.vo.mybatis.ArticleT;

public interface ITransactionalService {

    public Boolean updateArticle(ArticleT articleT);

}
