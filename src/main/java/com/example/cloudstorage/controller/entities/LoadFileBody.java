package com.example.cloudstorage.controller.entities;

import org.springframework.web.multipart.MultipartFile;

public class LoadFileBody {
    private String hash;
    private MultipartFile file;

    public LoadFileBody(String hash, MultipartFile file) {
        this.hash = hash;
        this.file = file;
    }

    public String getHash() {
        return hash;
    }

    public MultipartFile getFile() {
        return file;
    }
}
