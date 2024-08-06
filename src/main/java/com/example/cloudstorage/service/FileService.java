package com.example.cloudstorage.service;

import com.example.cloudstorage.controller.entities.LoadFileBody;
import com.example.cloudstorage.exception.InvalidDataException;
import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.model.UserInfo;

import java.io.IOException;

public interface FileService {
    void uploadFile(LoadFileBody loadFileBody, String fileName, UserInfo user) throws IOException, IllegalAccessException;

    void deleteFile(UserInfo user, String fileName);

    void changeFileName(UserInfo user, String oldFileName, String newFileName);

    UserFileInfo getData(UserInfo user, String fileName) throws InvalidDataException;
}
