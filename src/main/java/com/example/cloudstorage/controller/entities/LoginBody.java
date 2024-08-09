package com.example.cloudstorage.controller.entities;

public class LoginBody {
    public String login;
    public String password;

    public LoginBody(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
