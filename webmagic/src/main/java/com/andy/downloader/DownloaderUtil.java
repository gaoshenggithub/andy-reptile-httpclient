package com.andy.downloader;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.UUID;

public class DownloaderUtil {
    /**
     * 连接池
     *
     * @return
     */
    public static PoolingHttpClientConnectionManager getPoolingConnection() {
        return new PoolingHttpClientConnectionManager();
    }

    /**
     * 设置请求参数
     *
     * @return
     */
    public static RequestConfig getRequestConfig() {
        return RequestConfig.custom().
                setSocketTimeout(10000).
                setConnectionRequestTimeout(10000).
                setConnectTimeout(10000).build();
    }

    /**
     * 下载图片
     */
    public static void downloadImage(String imageUrl) throws Exception {
        CloseableHttpClient client = HttpClients
                .custom()
                .setConnectionManager(DownloaderUtil.getPoolingConnection())
                .build();
        HttpGet httpGet = new HttpGet(new URI(imageUrl));

        try (CloseableHttpResponse response = client.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    String extName = imageUrl.substring(imageUrl.lastIndexOf("."));
                    response.getEntity().writeTo(new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\51SMT\\com\\"
                            + UUID.randomUUID().toString().replace("-","")+extName
                    )));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
