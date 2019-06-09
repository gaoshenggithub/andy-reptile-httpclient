package com.andy.pool;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * client连接池
 */
public class HttpClientPoolDemo {
    public static PoolingHttpClientConnectionManager getConnectionPool() {
        return new PoolingHttpClientConnectionManager();
    }

    @Test
    public void doGet() {
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(
                HttpClientPoolDemo.getConnectionPool()).build();
        HttpGet httpGet = new HttpGet("https://mkt.51job.com/tg/sem/pz_2018.html");
        httpGet.setConfig(HttpClientPoolDemo.getRequestConfig());
        try (
                CloseableHttpResponse response = client.execute(httpGet)
        ) {
            if (response.getStatusLine().getStatusCode() == 200) {
                String s = EntityUtils.toString(response.getEntity(), "gbk");
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static RequestConfig getRequestConfig(){
        return RequestConfig.custom()
                .setConnectTimeout(10000)//创建连接最长时间
                .setConnectionRequestTimeout(10000)//获取请求连接最大时间
                .setSocketTimeout(10000)//获取传输数据最大时间
                .setProxy(new HttpHost("47.105.177.77",10002))
                //.setProxy(new HttpHost("218.5.189.232",3128))
                .build();
    }
}
