package com.example.cloudstorage.service;

import com.example.cloudstorage.controller.entities.ListItem;
import com.example.cloudstorage.controller.entities.LoadFileBody;
import com.example.cloudstorage.model.UserFileInfo;

import java.io.IOException;
import java.util.Collection;

public interface FileService {
    void uploadFile(LoadFileBody loadFileBody, String fileName, String userName) throws IOException, IllegalAccessException;

    Collection<ListItem> getFileListByUser(String userName, Integer limit);

    void deleteFile(String userName, String fileName);

    void changeFileName(String userName, String oldFileName, String newFileName);

    UserFileInfo getData(String userName, String fileName);
}
