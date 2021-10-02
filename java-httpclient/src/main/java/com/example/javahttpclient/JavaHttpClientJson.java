package com.example.javahttpclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class JavaHttpClientJson {


    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/test/json");

        TestParam param = new TestParam("Dongwoo", "Victor");
        ObjectMapper objectMapper = new ObjectMapper();

        StringEntity stringEntity = new StringEntity(objectMapper.writeValueAsString(param));
        httpPost.setEntity(stringEntity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpPost);


        System.out.println("=========================== start ============================");

        System.out.println(objectMapper.writeValueAsString(param));
        System.out.println("Http Status Code : " + response.getStatusLine().getStatusCode());

        System.out.println("============================ end =============================");
    }
}

class TestParam implements Serializable {
    private String name;
    private String nickname;

    public TestParam(String name, String nickname){
        this.name = name;
        this.nickname = nickname;
    }

    public String getName(){
        return name;
    }

    public String getNickname(){
        return nickname;
    }
}