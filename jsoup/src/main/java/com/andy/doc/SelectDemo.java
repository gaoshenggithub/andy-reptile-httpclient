package com.andy.doc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.net.URL;

/**
 * 选择器组合使用
 * el#id
 * el.class
 * el[attr]
 * 任何选择器
 * attr child
 * parent > child
 * parent > *
 */
public class SelectDemo {
    /**
     * el#id组合器使用
     */
    @Test
    public void getElementId() throws Exception {
        Document doc = Jsoup.parse(new URL("https://mkt.51job.com/careerpost/default_res.php"), 10000);
        Elements select = doc.select("ul#area_channel_layer_list");
        select.forEach(System.out::println);
    }

    /**
     * el.class组合器使用
     */
    @Test
    public void getElementClass() throws Exception {
        Document doc = Jsoup.parse(new URL("https://mkt.51job.com/careerpost/default_res.php"), 10000);
        Elements select = doc.select("div.ht");
        select.forEach(System.out::println);
    }

    /**
     * el[attr]
     */
    @Test
    public void getElements() throws Exception {
        Document doc = Jsoup.parse(new URL("https://mkt.51job.com/careerpost/default_res.php"), 10000);
        Elements select = doc.select("a[href]");
        select.forEach(System.out::println);
    }

    /**
     * 任何选择器
     */
    @Test
    public void getAnyElement() throws Exception {
        Document doc = Jsoup.parse(new URL("https://mkt.51job.com/careerpost/default_res.php"), 10000);
        Elements select = doc.select("div.clst>div.e>span");
        select.forEach(System.out::println);
    }

    /**
     * attr child
     */
    @Test
    public void getAttrChild() throws Exception {
        Document doc = Jsoup.parse(new URL("https://mkt.51job.com/careerpost/default_res.php"), 10000);
        Elements select = doc.select(".e>span");
        select.forEach(System.out::println);
    }

    /**
     * parent > child
     *
     */
    @Test
    public void getParentChild()throws Exception{
        Document doc = Jsoup.parse(new URL("https://mkt.51job.com/careerpost/default_res.php"), 10000);
        Elements select = doc.select("div>span");
        select.forEach(System.out::println);
    }

    /**
     * parent>*
     */
    @Test
    public void getParentAll()throws Exception{
        Document doc = Jsoup.parse(new URL("https://mkt.51job.com/careerpost/default_res.php"), 10000);
        Elements select = doc.select("div>*");
        select.forEach(System.out::println);
    }
}
