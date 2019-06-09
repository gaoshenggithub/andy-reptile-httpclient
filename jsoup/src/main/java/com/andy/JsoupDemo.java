package com.andy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class JsoupDemo {
    /**
     * Jsoup解析url
     */
    @Test
    public void jsoupUrl() throws Exception {
        Document doc = Jsoup.parse(
                new URL("https://mkt.51job.com/tg/sem/pz_2018.html?from=baidupz"),
                10000);
        String html = doc.html();
        Document parse = Jsoup.parse(html);
        Elements es = parse.select("div.clst div.e");
        new ArrayList<Object>() {{
            es.forEach(e -> {
                Elements span_e = e.select("div.e span");
                add(new ArrayList<Object>() {{ span_e.forEach(e2 -> add(e2.text()));}});
            });
        }}.forEach(System.out::println);
    }

    /**
     * Jsoup解析文件
     */
    @Test
    public void jsoupFile()throws Exception{
        Document doc = Jsoup.parse(new File("C:\\Users\\Administrator\\Desktop\\job.html"), "gbk");
        String es = doc.getElementsByTag("title").first().text();
        System.out.println("es = " + es);
    }
}
