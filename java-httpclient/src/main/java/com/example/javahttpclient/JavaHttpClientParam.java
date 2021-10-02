package com.example.javahttpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class JavaHttpClientParam {
    public static void main(String[] args) throws IOException {

        BufferedReader br;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/test/params");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("name", "Dong Woo"));
        params.add(new BasicNameValuePair("nickname", "victor"));
        HttpEntity entity = new UrlEncodedFormEntity(params);
        httpPost.setEntity(entity);

        CloseableHttpResponse response = client.execute(httpPost);

        System.out.println("=========================== start ============================");

        System.out.println("Http Status Code : " + response.getStatusLine().getStatusCode());

        System.out.println("============================ end =============================");
    }
}
