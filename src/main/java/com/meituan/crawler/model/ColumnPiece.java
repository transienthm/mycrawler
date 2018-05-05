package com.meituan.crawler.model;

import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/12/12
 * \* Time: 15:33
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class ColumnPiece {
    private String title;
    private String voice;
    private List<String> img;

    public ColumnPiece() {
        this.img = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "ColumnPiece{" +
                "title='" + title + '\'' +
                ", voice='" + voice + '\'' +
                ", img=" + img +
                '}';
    }
}
