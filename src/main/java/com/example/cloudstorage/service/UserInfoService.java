package com.example.cloudstorage.service;

import com.example.cloudstorage.exception.InvalidDataException;
import com.example.cloudstorage.exception.UserIsNullException;
import com.example.cloudstorage.model.UserInfo;

public interface UserInfoService {
    UserInfo getUserByName(String userName) throws InvalidDataException, UserIsNullException;
}
