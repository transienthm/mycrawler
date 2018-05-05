package com.meituan.crawler.parse.dedao;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.meituan.crawler.dao.ColumnPieceDAO;
import com.meituan.crawler.model.ColumnPiece;
import com.meituan.crawler.parse.utils.ParseUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2017/12/12
 * \* Time: 10:12
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Component
public class TuZigh {
    public static final String BASE_URL = "http://www.tuzigh.com/archiver/";
    private static final String LOCAL_DIR = "";
    private static Map<String, List<Document>> domInColumns = new HashMap<>();
    public static final Logger LOGGER = LoggerFactory.getLogger(TuZigh.class);

    File file = new File("E:\\BaiduNetdiskDownload\\得到app分享\\tuzi\\dedao.txt");

    @Autowired
    private ColumnPieceDAO columnPieceDAO;

    /**
     * 获取得到专栏的url
     * @return 专栏url的list
     */
    public List<String> getUrlsOfDedao() {
        List<String> res = new ArrayList<>();
        try {
            Document dom = ParseUtils.getDomFromUrl(new URL(BASE_URL));
            Elements address = dom.select("#content a[href]");
            for (Element element : address) {
                String columnUrl = BASE_URL + element.attr("href");
                res.add(columnUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取真实页码
     * @param columns
     */
    public void getPieceFromColumns(List<String> columns) {
        try {
            for (String column : columns) {
                //处理每一个专栏
                //当前页
                Document cur = ParseUtils.getDomFromUrl(new URL(column));
                //得到专栏名
                String columnName = getTitleFromDom(cur);
                //
                Elements pieces = cur.select("#content a[href]");
                for (Element piece : pieces) {
                    String pieceUrl = BASE_URL + piece.attr("href");
                    if (!pieceUrl.contains("page")) {
//                        System.out.println("pieceUrl = " + pieceUrl);
                        ColumnPiece columnPiece = getColumnPieceFromUrl(pieceUrl);
                        System.out.println(columnPiece);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void outputIntoFile(String url) {
        try {
            FileOutputStream out = new FileOutputStream(file, true);
            out.write(url.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTitleFromDom(Document dom) {
        String nav = dom.select("#nav").html();
        int start = nav.indexOf("</a>");
        String res = nav.substring(start < 0 ? 0 : start + 4, nav.length());
        System.out.println(res);
        return res;
    }
    /**
     * 得到专栏的最大页码
     * @param column
     * @return
     */
    public int getMaxPageOfColumn(String column) {
        int res = 0;
        int pageNum = column.contains("page=") ? Integer.valueOf(column.split("page=")[1]) : -1;
        boolean flag = true;
        try {
            while (flag && pageNum <= res) {
                Document index = ParseUtils.getDomFromUrl(new URL(column));
                Elements pages = index.select(".page a[href]");
                Elements curPage = index.select(".page strong");

                int curPageNum = getCurPage(curPage.toString());
                if (pages.size() > 0) {
                    for (int i = pages.size() - 1; i>=0;i--) {
                        Element page = pages.get(i);
                        String href = page.attr("href");
                        String[] split = href.split("page=");
                        pageNum = Integer.valueOf(split[1]);
                        if (curPageNum > pageNum && curPageNum == res) {
                            flag = false;
                            break;
                        }
                        res = pageNum > res ? pageNum : res;
                        column = BASE_URL + href.split("page=")[0] + "page=" + res;
                    }
                }else {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取专栏内的所有url
     * @param column
     * @param maxPage
     * @return
     */
    public List<String> getUrlInColumn(String column, int maxPage) {
        List<String> res = new ArrayList<>();
        for (int i = 1; i < maxPage; i++) {
            String url = column + "&page=" + i;
            System.out.println("************************");
            System.out.println("************************");
            System.out.println("************************");
            System.out.println(url);
            System.out.println("************************");
            System.out.println("************************");
            res.add(url);
        }
        return res;
    }

    public void downloadASingleColumn(String url) {
        
    }

    private int getCurPage(String url) {
        int start = url.indexOf("[");
        int end = url.indexOf("]");
        String substring = url.substring(start + 1, end);
        return Integer.valueOf(substring);
    }

    /**
     * 获取到专栏的音频及图片资源
     * @param url
     * @return
     */
    public ColumnPiece getColumnPieceFromUrl(String url) {

        ColumnPiece columnPiece = new ColumnPiece();
        try {
            String html = getUrlAfterJs(url);
            Document dom = ParseUtils.getDomFromUrl(html, BASE_URL);
            String title = getTitleFromDom(dom);
            columnPiece.setTitle(title);

//            outputIntoFile(title);
            Elements elements = dom.select("#content");
            String[] split = elements.toString().split("<br>");
            for (String s : split) {
                int start = s.indexOf("http");
                int end = s.length();
                if (start < 0) {
                    break;
                }
                String content = s.substring(start, end);
                outputIntoFile(content);
                if (content.contains("m4a")) {
                    columnPiece.setVoice(content);
                    System.out.println("音频文件:" + content);
                } else {
                    List<String> img = columnPiece.getImg();
                    img.add(content);
                    System.out.println("图片文件：" + content);
                    columnPiece.setImg(img);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnPiece;
    }

    private String getUrlAfterJs(String url) {
        String res = "";
        try {
            WebClient wc = new WebClient();

            wc.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
            wc.getOptions().setCssEnabled(false); //禁用css支持
            wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
            wc.getOptions().setTimeout(10000);
            HtmlPage page = wc.getPage(url);
            res = page.asXml(); //以xml的形式获取响应文本
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

/*    public void goCrawler() {
        TuZigh tuZigh = new TuZigh();
        try {
            List<String> columns = tuZigh.getUrlsOfDedao();
            for (String column : columns) {
                int maxPageOfColumn = tuZigh.getMaxPageOfColumn(column);
                List<String> urlInColumn = tuZigh.getUrlInColumn(column, maxPageOfColumn);
                tuZigh.getPieceFromColumns(urlInColumn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static void main(String[] args) {
        TuZigh tuZigh = new TuZigh();
        try {
//            List<String> columns = tuZigh.getUrlsOfDedao();
//            for (String column : columns) {
            String column = "http://www.tuzigh.com/archiver/?fid-300.html";
                int maxPageOfColumn = tuZigh.getMaxPageOfColumn(column);
                List<String> urlInColumn = tuZigh.getUrlInColumn(column, maxPageOfColumn);
                tuZigh.getPieceFromColumns(urlInColumn);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
