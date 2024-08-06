package com.example.cloudstorage.service;

import com.example.cloudstorage.controller.entities.LoadFileBody;
import com.example.cloudstorage.exception.InvalidDataException;
import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.repository.UserFileInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DefaultFileService implements FileService {
    private final UserFileInfoRepository userFileInfoRepository;
    private static final Logger log = LoggerFactory.getLogger(DefaultFileService.class);

    @Autowired
    public DefaultFileService(UserFileInfoRepository userFileInfoRepository) {
        this.userFileInfoRepository = userFileInfoRepository;
    }

    @Override
    public void uploadFile(LoadFileBody loadFileBody, String fileName, UserInfo user)
            throws IOException, IllegalAccessException {
        log.debug("uploadFile: user = " + user.getUsername() + ", fileName = " + fileName);

        for (UserFileInfo data : user.getListFilesInfo()) {
            if (data.getFileName().equals(fileName)) {
                String exceptionMessage = "File name " + fileName + ", already exists";
                IllegalAccessException illegalAccessException = new IllegalAccessException(exceptionMessage);
                log.error(exceptionMessage, illegalAccessException);
                throw illegalAccessException;
            }
        }

        UserFileInfo data = new UserFileInfo();
        data.setFileName(fileName);
        data.setData(loadFileBody.getFile().getBytes());
        data.setUser(user);

        userFileInfoRepository.save(data);
    }

    @Override
    public void deleteFile(UserInfo user, String fileName) {
        log.debug("deleteFile: userName = " + user.getUsername() + ", fileName = " + fileName);

        user.getListFilesInfo().forEach(data -> {
            if (data.getFileName().equals(fileName)) {
                userFileInfoRepository.delete(data);
            }
        });
    }

    @Override
    public void changeFileName(UserInfo user, String oldFileName, String newFileName) {
        log.debug("changeFileName: userName = " + user.getUsername() + ", oldFileName = " + oldFileName + ", newFileName = " + newFileName);

        user.getListFilesInfo().forEach(data -> {
            if (data.getFileName().equals(oldFileName)) {
                data.setFileName(newFileName);
                userFileInfoRepository.saveAndFlush(data);
            }
        });
    }

    @Override
    public UserFileInfo getData(UserInfo user, String fileName) throws InvalidDataException {
        log.debug("getData: userName = " + user.getUsername() + ", fileName = " + fileName);

        for (UserFileInfo data : user.getListFilesInfo()) {
            if (data.getFileName().equals(fileName)) {
                return data;
            }
        }
        InvalidDataException invalidDataException = new InvalidDataException("Data not found");
        log.error("getData: Input data fileName - " + fileName + ", not exist", invalidDataException);
        throw invalidDataException;
    }
}
