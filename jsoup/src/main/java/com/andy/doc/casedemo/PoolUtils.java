package com.andy.doc.casedemo;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.UUID;

public class PoolUtils {
    public static PoolingHttpClientConnectionManager getPoolingConnection() {
        return new PoolingHttpClientConnectionManager();
    }

    public static RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setConnectTimeout(10000)
                .build();
    }

    public static String doGet(String url)throws Exception {

        CloseableHttpClient client = HttpClients
                .custom()
                .setConnectionManager(PoolUtils.getPoolingConnection())
                .build();
        HttpGet httpGet = new HttpGet(new URI(url));
        httpGet.setConfig(PoolUtils.getRequestConfig());
        try (CloseableHttpResponse response = client.execute(httpGet)) {
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    return EntityUtils.toString(response.getEntity(), "gbk");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean doImage(String url)throws Exception {
        CloseableHttpClient client = HttpClients
                .custom()
                .setConnectionManager(PoolUtils.getPoolingConnection())
                .build();
        HttpGet httpGet = new HttpGet(new URI(url));
        httpGet.setConfig(PoolUtils.getRequestConfig());
        try (CloseableHttpResponse response = client.execute(httpGet);
        ) {
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    String extName = url.substring(url.lastIndexOf("."));
                    String imgName = UUID.randomUUID().toString().replace("-", "") + extName;
                    response.getEntity().writeTo(new FileOutputStream(new File("E:\\OpenSources\\iamges\\"+imgName)));
                    return true;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
