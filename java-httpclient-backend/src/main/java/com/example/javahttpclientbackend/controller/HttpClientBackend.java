package com.example.javahttpclientbackend.controller;

import com.example.javahttpclientbackend.domain.TestParam;
import com.example.javahttpclientbackend.domain.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.http.HttpResponse;

@RestController
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HttpClientBackend {

    @PostMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @PostMapping("/test/params")
    public ResponseEntity testParams(@RequestParam("name") String name, @RequestParam("nickname") String nickName){
        log.info("=========================== start ============================");
        log.info("Name : " + name);
        log.info("Nickname : " + nickName);
        log.info("============================ end =============================");

        return ResponseEntity.ok().build();
    }

    @PostMapping("/test/json")
    public ResponseEntity testJsonParams(@RequestBody TestParam param){
        log.info("=========================== start ============================");
        log.info("Name : " + param.getName());
        log.info("Nickname : " + param.getNickname());
        log.info("============================ end =============================");

        return ResponseEntity.ok().build();
    }

    @PostMapping(value ="/test/multipart")
    public ResponseEntity testMultipart(MultipartFile file) throws Exception {
        if(file == null){
            throw new Exception();
        }

        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        while((line = br.readLine()) != null){
            log.info(line);
        }

        return  ResponseEntity.ok().build();
    }
}
