package com.andy.post;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HttpPostDemo {
    /**
     * Post请求不带参数
     */
    @Test
    public void post1() {
        HttpPost httpPost = new HttpPost("https://mkt.51job.com/tg/sem/pz_2018.html");
        try (
                CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(httpPost);
        ) {
            if (response.getStatusLine().getStatusCode() == 200) {
                String s = EntityUtils.toString(response.getEntity(), "gbk");
                System.out.println(s);
            }
        } catch (Exception e) {
        }
    }


    /**
     * Post请求带参数
     */
    @Test
    public void post2() {
        URIBuilder builder = null;
        HttpPost httpPost = null;
        try {
            builder = new URIBuilder("https://mkt.51job.com/tg/sem/pz_2018.html");
            List<NameValuePair> params = new ArrayList<NameValuePair>() {{
                add(new BasicNameValuePair("from","baidupz"));
            }};
            builder.setParameters(params);
            httpPost = new HttpPost(builder.build());
        } catch (Exception e) {
        }
        try (
                CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(httpPost)

        ) {
            if (response.getStatusLine().getStatusCode() == 200) {
                String s = EntityUtils.toString(response.getEntity(),"gbk");
                System.out.println("s = " + s);
            }
        } catch (Exception e) {
        }
    }
}
