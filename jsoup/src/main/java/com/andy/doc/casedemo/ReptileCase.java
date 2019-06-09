package com.andy.doc.casedemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReptileCase {
    private String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&wq=%E6%89%8B%E6%9C%BA&pvid=e6dd60a56fd142fbaab534275d113124&page=";

    private ObjectMapper MAPPER = new ObjectMapper();

    public List<Object> getJD() throws Exception {
        return new ArrayList<Object>() {{
            for (int i = 1; i < 9; i += 2) {
                Document doc = Jsoup.parse(new URL(url), 10000);
                Elements spuEle = doc.select("div#J_goodsList>ul>li");
                Elements li = doc.select("div#J_goodsList>ul>li li");
                System.out.println("li = " + li.size());
                int size = spuEle.size();
                System.out.println("size = " + size);
                new ArrayList<Object>() {{
                    spuEle.forEach(e -> {
                        System.out.println("e = " + e);
                        //获取spu
                        String spu = e.attr("data-spu");
                        Elements skuEle = e.select("li.ps-item");
                        skuEle.forEach(e2 ->
                                add(new HashMap<String, Object>() {{
                                    put("spu", spu);
                                    //获取sku
                                    String sku = e2.select("[data-sku]").attr("data-sku");
                                    put("sku", sku);
                                    //获取商品详情
                                    String shopUrl = "https://item.jd.com/" + sku + ".html";
                                    put("shopUrl", shopUrl);
                                    //获取商品图片
                                    String img = "http:" + e2.select("img[data-sku]").attr("data-lazy-img");
                                    try {
                                        PoolUtils.doImage(img.replace("/n9/", "/n1/"));
                                        //获取价格
                                        String s = PoolUtils.doGet("https://p.3.cn/prices/mgets?skuIds=J_" + sku);
                                        //[{"cbf":"0","id":"J_100005702160","m":"100000.00","op":"1599.00","p":"1599.00"}]
                                        double p = MAPPER.readTree(s).get(0).get("p").asDouble();
                                        put("price", p);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    //获取商品标题
                                    try {
                                        String shopHtml = PoolUtils.doGet(shopUrl);
                                        String title = Jsoup.parse(shopHtml).select("div.sku-name").text();
                                        if (StringUtils.isEmpty(title)) {
                                            title = Jsoup.parse(shopHtml).select("div.sku-name").text();
                                        }
                                        put("title", title);
                                    } catch (Exception e) {

                                    }

                                }})
                        );
                    });
                }};
            }
        }};
    }

    @Test
    public void test2() throws Exception {
        getJD().forEach(System.out::println);
    }

    @Test
    public void test3() throws Exception {
        String shopHtml = PoolUtils.doGet("https://item.jd.com/100003884562.html");
        String title = Jsoup.parse(shopHtml).select("div.sku-name").text();
       /* if (StringUtils.isEmpty(title)){
            title=Jsoup.parse(shopHtml).select("div.sku-name").text();
        }*/
        System.out.println("title = " + title);
    }
}
