package com.hapjuhasil.server.controller;

import com.hapjuhasil.server.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TestController {

    private S3Service s3Service;
    @GetMapping("/test")
    public String basicTest(){
        return "test";
    }

    @PostMapping(path = "/uploadTest",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImageTest(@RequestPart MultipartFile file) throws IOException {
        return this.s3Service.upload(file);
    }

    @DeleteMapping("/delete")
    public String deleteTest(String name){
        this.s3Service.deleteFile(name);
        return "success";
    }
}