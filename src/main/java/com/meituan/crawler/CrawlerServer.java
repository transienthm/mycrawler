package com.meituan.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/11/19
 * \* Time: 17:16
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@SpringBootApplication
@ImportResource(value = "classpath:spring.xml")
public class CrawlerServer {
    public static void main(String[] args) {
        SpringApplication.run(CrawlerServer.class, args);
    }
}
