package com.meituan.crawler.model;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/11/16
 * \* Time: 16:09
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
public class Article {
    private String header;
    private String content;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
