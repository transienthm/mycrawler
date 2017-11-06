package com.meituan.httpclient;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by wangbin on 2017/11/5.
 */
public class HttpClientTest {
    @Test
    public void testGet() {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestBuilder builder = RequestBuilder.get("http://www.ip138.com");
        HttpGet httpGet = new HttpGet(builder.build().getURI());
        CloseableHttpResponse execute = null;
        try {
            execute = client.execute(httpGet);
            HttpEntity entity = execute.getEntity();
            System.out.println(entity.getContentType());
            String s = EntityUtils.toString(entity, Charset.forName("gb2312"));
            System.out.println(s);
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
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testGet2() {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestBuilder builder = RequestBuilder.get("http://www.ip138.com");
        HttpGet httpGet = new HttpGet(builder.build().getURI());
        try {
            String execute = client.execute(httpGet, new ResponseHandler<String>() {
               @Override
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    HttpEntity entity = httpResponse.getEntity();
                    String s = EntityUtils.toString(entity, Charset.forName("gb2312"));
                    return s;
                }
            });
            System.out.println(execute);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGet4() {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestBuilder builder = RequestBuilder.get("http://www.ip138.com");
        HttpGet httpGet = new HttpGet(builder.build().getURI());
        try {
            String execute = client.execute(httpGet, (HttpResponse httpResponse)->{
                HttpEntity entity = httpResponse.getEntity();
                String page = EntityUtils.toString(entity, Charset.forName("gb2312"));
                return page;
            });
            System.out.println(execute);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGet3() throws IOException {
        Response response = Request.Get("http://www.ip138.com").execute();
        String s = response.returnContent().asString(Charset.forName("gb2312"));
        System.out.println(s);
    }

    @Test
    public void testPost() throws IOException {
        Response response = Request.Post("http://api.caijiwa.com/api/proxy/free/get").bodyForm(new BasicNameValuePair("f", "2")).execute();
        String page = response.returnContent().asString();
        System.out.println(page);
    }
}
