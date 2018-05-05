package com.meituan.crawler.model;

import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/11/21
 * \* Time: 14:11
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
public class Begger {
    String name;
    int money;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
