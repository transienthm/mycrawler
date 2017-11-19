package com.meituan.crawler.dao;

import com.alibaba.druid.filter.AutoLoad;
import com.meituan.crawler.model.Article;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/11/19
 * \* Time: 16:53
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Repository
public class ArticleDAO {

    private static final String BASE_PACKAGE = "com.meituan.crawler.mapper.Article.";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public void addArticle(Article article) {
        sqlSessionTemplate.insert(BASE_PACKAGE + "insert", article);
    }

    public List<Article> listArticle() {
        List<Article> res = sqlSessionTemplate.selectList(BASE_PACKAGE + "select");
        return res;
    }
}
