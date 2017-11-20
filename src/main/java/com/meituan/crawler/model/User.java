package com.meituan.crawler.model;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/11/20
 * \* Time: 20:44
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
public class User {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
