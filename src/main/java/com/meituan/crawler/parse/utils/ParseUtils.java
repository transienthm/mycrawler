package com.meituan.crawler.parse.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/12/12
 * \* Time: 10:07
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class ParseUtils {
    public static final int DELAY_TIME = 600000000;

    public static Document getDomFromUrl(URL url) {
        try {
            Document dom = Jsoup.parse(url, DELAY_TIME);
            return dom;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Document getDomFromUrl(String html, String baseUrl) {
        try {
            Document dom = Jsoup.parse(html, baseUrl);
            return dom;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
