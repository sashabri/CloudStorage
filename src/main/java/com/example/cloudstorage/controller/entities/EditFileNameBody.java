package com.example.cloudstorage.controller.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditFileNameBody {

    @JsonProperty("filename")
    private String fileName;

    public EditFileNameBody(){};

    public EditFileNameBody(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
