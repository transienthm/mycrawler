package com.meituan.crawler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by wangbin on 2017/11/5.
 */
public class Demo {
    public static void main(String[] args) {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestBuilder builder = RequestBuilder.get("http://www.qq.com");
        HttpGet httpGet = new HttpGet(builder.build().getURI());

        CloseableHttpResponse execute = null;
        try {
            execute = client.execute(httpGet);
            HttpEntity entity = execute.getEntity();
            String charset = getCharsetFromContentType(entity.getContentType());
            String page = EntityUtils.toString(entity, Charset.forName(charset));
            System.out.println(page);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (execute != null) {
                try {
                    execute.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getCharsetFromContentType(Header header) {
        String contentType = header.toString();
        String[] charset = contentType.split(";");
        String res = charset.length > 1 ? charset[1] : "";
        return res.split("=")[1];
    }

}
