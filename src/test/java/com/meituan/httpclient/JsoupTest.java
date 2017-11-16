package com.meituan.httpclient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by wangbin on 2017/11/6.
 */
public class JsoupTest {
    @Test
    public void testParse() {
        Document document = Jsoup.parse("<html><div>I'm div</div></html>");
        Elements divs = document.getElementsByTag("div");
        Iterator<Element> iterator = divs.iterator();
        while (iterator.hasNext()) {
            Element div = iterator.next();
            System.out.println(div.html());
        }
    }

}
