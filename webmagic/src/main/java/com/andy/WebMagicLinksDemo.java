package com.andy;

import com.andy.downloader.DownloaderUtil;
import org.junit.Test;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.util.List;

/**
 * WebMagic获取链接
 * links()
 */
public class WebMagicLinksDemo implements PageProcessor {

    @Override
    public void process(Page page) {
        page.getHtml().$("div.ctitle>a").links().all().forEach(e -> {
            try {
                DownloaderUtil.downloadImage(e);
            } catch (Exception e1) {

            }
        });

    }

    private Site site = Site.me();

    @Override
    public Site getSite() {
        return this.site;
    }

    @Test
    public void webLinks() {
        Spider.create(new WebMagicLinksDemo())
                .thread(5)
                .addUrl("http://my.51job.com/art_sc/searchresult.php?kw=Java&sf=1&sc=00&st=0")
                .addPipeline(new FilePipeline("E:\\OpenSources\\pipeFile\\"))
                .setScheduler(new RedisScheduler(new JedisPool("47.105.177.77", 6379)))
                .run();
    }
}
