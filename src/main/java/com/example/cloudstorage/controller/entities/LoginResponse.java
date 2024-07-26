package com.example.cloudstorage.controller.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
    @JsonProperty(value = "auth-token")
    private String authToken;
    public LoginResponse(String authToken) {
        this.authToken = authToken;
    }
    public String getAuthToken() {
        return authToken;
    }
}
