package com.meituan.crawler.parse.jiemian;

import com.meituan.crawler.model.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/11/16
 * \* Time: 15:53
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class TechParse {
    public static final int DELAY_TIME = 10000;


    public static void main(String[] args) {
        String s = TechParse.parseArticle();
        System.out.println(s);
    }

    public static String parseArticle() {
        String res = "";
        try {
            Document dom = Jsoup.parse(new URL("http://www.jiemian.com/lists/65.html"), DELAY_TIME);
            Elements articleAddress = dom.select(".news-header a[href]");
            String url = "";
            for (Element element : articleAddress) {
                System.out.println(element.attr("href"));
                url = element.attr("href");
            }
            res = getArticleContentByURL(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String getArticleContentByURL(URL url) {
        StringBuilder sb = new StringBuilder();
        try {
            Document dom = Jsoup.parse(url, DELAY_TIME);
            Article article = new Article();
            Elements header = dom.select(".article-header");


            sb.append(header.select("h1").html()).append(header.select("a"));
            article.setHeader(sb.toString());
            sb.setLength(0);

            Elements content = dom.select(".article-content p");
            for (Element p : content) {
                sb.append(p.html());
                sb.append("\n");
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
