package com.andy;

import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.CssSelector;

/**
 * WebMagic案例
 * CSS $
 * XPath
 * 正则表达式
 */
public class WebMagicDemo implements PageProcessor {

    @Override
    public void process(Page page) {
        //使用CSS选择器
        page.putField("div ctitle====>$", page.getHtml()
                .$("div.ctitle>a")
                .links()
                .all()
                .size());

        //CSS选择器
        page.putField("div ctitle====>css", page.getHtml()
                .css("div.ctitle>a")
                .links()
                .all()
                .size());

        //select===>Xpath
        page.putField("div ctitle====>select", page.getHtml()
                .select(new CssSelector("div.ctitle>a"))
                .links()
                .all()
                .size());

        //XPath
        page.putField("div ctitle====>Xpath", page.getHtml()
                .xpath("//div[@class=ctitle]/a")
                .links()
                .all()
                .size());

        //正则表达式
        page.putField("div ctitle====>正则表达式", page.getHtml()
                .$("div.ctitle>a")
                .regex(".*IT认证培训“火爆广东”.*").all());
    }

    private Site site = Site
            .me()
            .setSleepTime(100)
            .setTimeOut(100000)
            .setRetryTimes(1000)
            .setRetrySleepTime(1000);

    @Override
    public Site getSite() {
        return this.site;
    }

    @Test
    public void webMagicDemo() {
        Spider.create(new WebMagicDemo())
                .addUrl("http://my.51job.com/art_sc/searchresult.php?kw=Java&sf=1&sc=00&st=0")
                .thread(5)
                .run();
    }
}
