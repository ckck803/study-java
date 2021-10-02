package com.example.javahttpclient;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.text.html.parser.Entity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class JavaHttpclientApplication {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/hello");

        CloseableHttpResponse response = client.execute(httpPost);

        System.out.println("============================ body =============================");

        String inputLine;
        StringBuffer result = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        while ((inputLine = br.readLine()) != null) {
            result.append(inputLine);
        }

        System.out.println(result);

        System.out.println("============================ body =============================");

        br.close();
        client.close();
    }
}
