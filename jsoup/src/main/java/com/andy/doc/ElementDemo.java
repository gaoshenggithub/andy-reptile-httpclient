package com.andy.doc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.net.URL;

/**
 * 从元素中获取数据
 * 元素中获取id className attr attributes text
 */
public class ElementDemo {
    /**
     * 获取元素内容
     */
    @Test
    public void getElementContext() throws Exception {
        Document doc = Jsoup
                .parse(new URL(
                                "https://mkt.51job.com/careerpost/default_res.php"),
                        10000);
        Element html = doc.getElementById("languagelist");
        getId(html);
        getClassName(html);
        getAttr(html);
        getAttributes(html);
        getText(html);
        
    }

    private void getText(Element html) {
        String text = html.text();
        System.out.println("text = " + text);
    }

    private void getAttributes(Element html) {
        Attributes attributes = html.attributes();
        attributes.forEach(System.out::println);
    }

    private void getAttr(Element html) {
        String id = html.attr("id");
        System.out.println("id = " + id);
    }

    private void getClassName(Element html) {
        String s = html.className();
        System.out.println("s===>" + s);
    }

    /**
     * 获取Id
     *
     * @param html
     */

    private void getId(Element html) {
        String id = html.id();
        System.out.println("id = " + id);
    }
}
