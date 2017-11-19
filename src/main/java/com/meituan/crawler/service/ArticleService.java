package com.meituan.crawler.service;

import com.meituan.crawler.dao.ArticleDAO;
import com.meituan.crawler.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/11/19
 * \* Time: 20:26
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDAO articleDAO;

    public List<Article> listArticle() {
        List<Article> res = articleDAO.listArticle();
        return res;
    }
}
