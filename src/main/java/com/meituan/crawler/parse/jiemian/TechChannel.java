package com.meituan.crawler.parse.jiemian;

import com.meituan.crawler.dao.ArticleDAO;
import com.meituan.crawler.model.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/11/16
 * \* Time: 15:53
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Component
public class TechChannel {
    public static final int DELAY_TIME = 10000;

    @Autowired
    ArticleDAO articleDAO;

/*    public static void main(String[] args) {
        TechParse techParse = new TechParse();
        List<Article> articles = TechParse.parseArticle();
        for (Article article : articles) {
            techParse.articleDAO.addArticle(article);
            System.out.println(article.getHeader());
        }
    }*/

    public List<Article> parseArticle() {
        List<Article> res = new ArrayList<>();
        try {
            Document dom = Jsoup.parse(new URL("http://www.jiemian.com/lists/65.html"), DELAY_TIME);
            Elements articleAddress = dom.select(".news-header a[href]");
            String url = "";
            for (Element element : articleAddress) {
                System.out.println(element.attr("href"));
                url = element.attr("href");
                Article article = new Article();
                getArticleContentByURL(new URL(url), article);
                res.add(article);
                articleDAO.addArticle(article);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void getArticleContentByURL(URL url, Article article) {
        StringBuilder sb = new StringBuilder();
        try {
            Document dom = Jsoup.parse(url, DELAY_TIME);
            Elements header = dom.select(".article-header");

            sb.append(header.select("h1").html()).append(header.select("a"));
            article.setHeader(sb.toString());
            sb.setLength(0);
            Elements content = dom.select(".article-content p");
            for (Element p : content) {
                sb.append(p.html());
                sb.append("\n");
            }
            article.setContent(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
