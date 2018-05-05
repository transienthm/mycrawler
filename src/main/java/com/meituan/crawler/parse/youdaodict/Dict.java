package com.meituan.crawler.parse.youdaodict;

import com.meituan.crawler.model.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: cab
 * \* Date: 2018/5/5
 * \* Time: 15:33
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class Dict {

    public static final int DELAY_TIME = 10000;

    public void searchWordAndWriteFile(List<String> words) {
        List<Article> res = new ArrayList<>();
        try {
            for (String word : words) {
                Document dom = Jsoup.parse(new URL("http://dict.youdao.com/w/" + word + "/"), DELAY_TIME);
                Elements meanings = dom.select(".trans-container ul");
                Element meaning = meanings.get(0);
                String separator = System.getProperty("line.separator");
                String meaningHtml = meaning.html().trim().replaceAll("<li>", "").trim().replaceAll("</li>", separator).trim();
                Elements pronounceEle = dom.select(".phonetic");
                Element engEle = pronounceEle.first();
                String phonetic = engEle.html().replaceAll("<span class=\"phonetic\">", "").replaceAll("</span>", "");
                String finalStr =prepareWord(word, phonetic, meaningHtml);
                writeFile("f://word1.txt", finalStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String prepareWord(String word, String phonetic, String html) {
        StringBuilder sb = new StringBuilder();
        String separator = System.getProperty("line.separator");
        sb.append(word).append(" ").append(phonetic).append(separator).append(html).append(separator);
        return sb.toString();
    }

    public void writeFile(String path, String str) {
        try {
            FileWriter writer = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(str);
            bw.newLine();
            bw.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getWords() {
        List<String> res = new ArrayList<>();
        try {
            // read file content from file
            FileReader reader = new FileReader("f://test13.txt");
            BufferedReader br = new BufferedReader(reader);
            String str = null;
            while ((str = br.readLine()) != null) {
                if (str.trim().isEmpty()) {
                    continue;
                }
                res.add(str);
            }
            br.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static void main(String[] args) {
        Dict dict = new Dict();
        List<String> words = dict.getWords();
        dict.searchWordAndWriteFile(words);
/*        for (String s : dict.getWords()) {
            System.out.println(s);
        }*/
    }
}
