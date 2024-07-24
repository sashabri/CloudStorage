package com.example.cloudstorage.controller;

import com.example.cloudstorage.controller.entities.LoadFileBody;
import com.example.cloudstorage.controller.entities.LoginBody;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.exception.InvalidDataException;

public class ParamsChecker {
    public static void checkShouldBeNotEmptyStr(String param) throws InvalidDataException{
        if (param == null || param.isEmpty()) {
            throw new InvalidDataException("Invalid data");
        }
    }

    public static void checkShouldBeNotEmptyLoginBody(LoginBody body) throws InvalidDataException{
        if (body == null) {
            throw new InvalidDataException("You json is empty");
        }
    }
    public static void checkShouldBeNotEmptyLoadFileBody(LoadFileBody body) throws InvalidDataException {
        if (body == null) {
            throw new InvalidDataException("You json is empty");
        }
    }

    public static void checkShouldBeNotEmptyUser(UserInfo user) {
        if (user == null) {
            throw new NullPointerException("User not found");
        }
    }
}


