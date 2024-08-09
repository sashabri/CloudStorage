package com.example.cloudstorage.controller.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListItem {
    @JsonProperty("filename")
    private String fileName;
    private Integer size;

    public ListItem(String fileName, Integer size) {
        this.fileName = fileName;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
