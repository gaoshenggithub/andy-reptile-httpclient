package com.andy.doc;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.net.URL;

/**
 * Doc文档获取参数id /标签/class/属性
 */
public class DocumentDemo {
    /**
     * 连接池
     *
     * @return
     */
    public static PoolingHttpClientConnectionManager getPoolConnection() {
        return new PoolingHttpClientConnectionManager();
    }

    /**
     * 请求配置
     *
     * @return
     */
    public static RequestConfig getRequestConfig() {
        return RequestConfig
                .custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000)
                .setProxy(new HttpHost("218.5.189.232", 3128))
                .build();
    }

    /**
     * getArgsById
     */
    @Test
    public void getId() throws Exception {
        Document doc = Jsoup.parse(new URL(
                        "https://mkt.51job.com/tg/sem/pz_2018.html?from=baidupz"),
                10000);
        Element byId = doc.getElementById("area_channel_layer_backdrop");
        System.out.println(byId);
    }

    /**
     * 根据标签获取
     */
    @Test
    public void getTag() throws Exception {
        Document doc = Jsoup.parse(new URL(
                        "https://mkt.51job.com/tg/sem/pz_2018.html?from=baidupz"),
                10000);
        Elements e = doc.getElementsByTag("title");
        e.forEach(System.out::println);
    }

    /**
     * 根据class属性获取
     */
    @Test
    public void getHtmlClass() throws Exception {
        Document doc = Jsoup.parse(new URL(
                        "https://mkt.51job.com/tg/sem/pz_2018.html?from=baidupz"),
                10000);
        Elements app = doc.getElementsByClass("e_icon");
        app.forEach(System.out::println);
    }

    /**
     * 获取属性
     */
    @Test
    public void getAttribute() throws Exception {
        Document doc = Jsoup.parse(new URL(
                        "https://mkt.51job.com/tg/sem/pz_2018.html?from=baidupz"),
                10000);
        Elements src = doc.getElementsByAttribute("src");
        src.forEach(System.out::println);
    }
}
