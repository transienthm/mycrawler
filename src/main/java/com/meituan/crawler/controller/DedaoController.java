package com.meituan.crawler.controller;

import com.meituan.crawler.parse.dedao.TuZigh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/12/12
 * \* Time: 17:34
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@RestController
public class DedaoController {
    @Autowired
    private TuZigh tuZigh;

    @RequestMapping(value = "/dedao", method = RequestMethod.GET)
    public void goCrawler() {
//        tuZigh.goCrawler();
    }
}
