package com.meituan.crawler;

import org.jsoup.Jsoup;

/**
 * Created by wangbin on 2017/11/5.
 */
public class Demo {
    public static void main(String[] args) {
        try {
            String body = Jsoup.connect("http://www.baidu.com").execute().body();

            System.out.println(body);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
