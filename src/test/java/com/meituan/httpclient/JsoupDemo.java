package com.meituan.httpclient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class JsoupDemo {
    @Test
    public void parseDomFromUrl() throws IOException {
        Document dom = Jsoup.parse(new URL("https://segmentfault.com/a/1190000011881759"), 10000);
        System.out.println(dom.html());
    }

    @Test
    public void parseDomByDom() throws IOException{
        Document dom = Jsoup.parse(new URL("https://segmentfault.com/a/1190000011881759"), 10000);
        Element img = dom.getElementById("icon4weChat");
        System.out.println(img.id());

        Elements hrefs = dom.getElementsByAttribute("href");
        for (Element href : hrefs) {
            System.out.println(href);
        }
    }

    @Test
    public void parseDomBySelect() throws IOException {
        Document dom = Jsoup.parse(new URL("https://segmentfault.com/a/1190000011881759"), 10000);
        Elements elements = dom.select("[data-user-id]");
        System.out.println("length = " + elements.size());
        System.out.println(".......................................................");
        for (Element e : elements) {
            System.out.println(e.outerHtml());
        }
    }

}
