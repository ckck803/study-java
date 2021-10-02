package com.example.javahttpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JavaHttpClientFluentApi {
    public static void main(String[] args) throws IOException {
        HttpResponse response = Request
                .Post("http://localhost:8080/hello")
                .execute()
                .returnResponse();

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        while((line = br.readLine()) != null){
            stringBuilder.append(line);
        }

        System.out.println("=========================== start ============================");
        System.out.println("Status Code: " + response.getStatusLine().getStatusCode());
        System.out.println("Content: " + stringBuilder.toString());
        System.out.println("============================ end =============================");
    }
}
