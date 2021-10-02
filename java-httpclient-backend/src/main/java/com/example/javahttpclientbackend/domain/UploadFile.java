package com.example.javahttpclientbackend.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadFile {
    String name;
    String nickname;
    MultipartFile file;
}
