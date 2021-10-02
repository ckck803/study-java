package com.example.javahttpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.MultipartBodyBuilder;

import java.io.File;
import java.io.IOException;

public class JavaHttpClientMultipart {
    public static void main(String[] args) throws IOException {
        String url = "http://localhost:8080/test/multipart";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addTextBody("name", "Dongwoo");
        multipartEntityBuilder.addTextBody("nickname", "Victor");
        multipartEntityBuilder.addBinaryBody("file",
                new File("/Users/dongwoo-yang/test.txt"),
                ContentType.APPLICATION_OCTET_STREAM,
                "test.txt");

        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);

        CloseableHttpResponse response = httpClient.execute(httpPost);


        System.out.println("=========================== start ============================");
        System.out.println("Status Code: " + response.getStatusLine().getStatusCode());
        System.out.println("============================ end =============================");
        httpClient.close();
    }
}
