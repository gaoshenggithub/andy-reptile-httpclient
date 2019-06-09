package com.andy.get;


import com.sun.javafx.fxml.builder.URLBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpGetDemo {

    /**
     * get请求不带参数
     */
    @Test
    public void get1() {
        HttpGet httpGet = new HttpGet("https://mkt.51job.com/tg/sem/pz_2018.html?from=baidupz");
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(httpGet);
        ) {
            if (response.getStatusLine().getStatusCode() == 200) {
                String s = EntityUtils.toString(response.getEntity(), "gbk");
                System.out.println("s = " + s);
            }
        } catch (Exception e) {
        }
    }

    /**
     * get请求带参数
     */
    @Test
    public void get2() {
        URIBuilder builder = null;
        HttpGet httpGet = null;
        try {
            builder = new URIBuilder("https://mkt.51job.com/tg/sem/pz_2018.html");
            builder.setParameters(new BasicNameValuePair("from","baidupz"));
            httpGet = new HttpGet(builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (
                CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(httpGet)
        ) {

            if (response.getStatusLine().getStatusCode() == 200) {
                String s = EntityUtils.toString(response.getEntity(),"gbk");
                System.out.println("s = " + s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
