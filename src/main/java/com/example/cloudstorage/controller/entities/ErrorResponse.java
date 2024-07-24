package com.example.cloudstorage.controller.entities;

public class ErrorResponse {
    private String message;

    private Integer id;

    public ErrorResponse(String message, Integer id) {
        this.message = message;
        this.id = id;
    }
    public String getMessage() {
        return message;
    }

    public Integer getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
