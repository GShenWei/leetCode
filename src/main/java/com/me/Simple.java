package com.me;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.http.HttpHeaders;
import java.util.*;
import java.util.stream.Collectors;

public class Simple {
    @Test
    public void sim() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> mm1 = new HashMap<>();
        Map<String, Object> mm2 = new HashMap<>();
        Map<String, Object> mm3 = new HashMap<>();
        mm1.put("1", 1);
        mm1.put("2", 2);
        mm2.put("3", 3);
        mm2.put("4", 4);
        mm3.put("5", 5);
        mm3.put("6", 6);
        mapList.add(mm1);
        mapList.add(mm2);
        mapList.add(mm3);
        Map<String, Object> map = mapList.stream().reduce(new HashMap<>(), (m1, m2) -> {
            m1.putAll(m2);
            return m1;
        });
        Map<String, Object> map2 = mapList.stream().collect(HashMap::new, HashMap::putAll, HashMap::putAll);

        for (Map.Entry<String, Object> e : map.entrySet()) {
            System.out.println(e.getKey() + "," + e.getValue());
        }
        for (Map.Entry<String, Object> e : map2.entrySet()) {
            System.out.println(e.getKey() + "," + e.getValue());
        }

    }

    @Test
    public void sim2() {
        String url = null;
        JSONObject jb = new JSONObject();
        jb.put("code", 0);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(300 * 1000)
                .setConnectTimeout(300 * 1000)
                .build();
        url = "http://URL:Port/地址";
        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        post.setHeader("Content-Type", "application/json;charset=utf-8");
        post.setHeader("Ahahaha", "application/json;charset=utf-8");
        StringEntity postingString = new StringEntity("fdfdfdfdf", "utf-8");
        post.setEntity(postingString);

        Header[] headers = post.getAllHeaders();


        //System.out.println(Arrays.stream(headers).map(it -> (it.getName() + "  " + it.getValue())).collect(Collectors.toList()));

        System.out.println();
        // HttpResponse response = httpClient.execute(post);
        // String content = EntityUtils.toString(response.getEntity());
        // System.out.println(content);

    }
}
