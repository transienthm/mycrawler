package com.meituan.crawler.controller;

import com.meituan.crawler.model.Article;
import com.meituan.crawler.parse.jiemian.TechChannel;
import com.meituan.crawler.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/11/19
 * \* Time: 17:18
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@RestController
public class ArticleController {

    @Autowired
    private TechChannel techChannel;

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public String listArticle() {
        List<Article> articles = articleService.listArticle();
        StringBuilder sb = new StringBuilder();

        for (Article article : articles) {
            sb.append("<article><header><h1>");
            sb.append(article.getHeader());
            sb.append("</h1></header>");
            sb.append(article.getContent());
            sb.append("</article>");
        }
        return sb.toString();
    }

    @RequestMapping(value = "/getarticle", method = RequestMethod.GET)
    public void getArticle() {
        techChannel.parseArticle();
    }


}
