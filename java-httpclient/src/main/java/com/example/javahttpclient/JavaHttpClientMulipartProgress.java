package com.example.javahttpclient;

import com.example.javahttpclient.wrapper.ProgressEntityWrapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;

public class JavaHttpClientMulipartProgress {
    public static void main(String[] args) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/test/multipart");

        HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addTextBody("name", "Dongwoo")
                .addTextBody("nickname", "Victor")
                .addBinaryBody("file"
                        , new File("/Users/dongwoo-yang/test.txt")
                        , ContentType.APPLICATION_OCTET_STREAM
                        , "test.txt")
                .build();

        ProgressEntityWrapper.ProgressListener progressListener = percentage -> {
            System.out.println("percentage : " + percentage);
        };
        httpPost.setEntity(new ProgressEntityWrapper(httpEntity, progressListener));

        CloseableHttpResponse response = httpClient.execute(httpPost);

        System.out.println("=========================== start ============================");
        System.out.println("Status Code: " + response.getStatusLine().getStatusCode());
        System.out.println("============================ end =============================");
        httpClient.close();
    }
}
