package com.example.cloudstorage.service;

import com.example.cloudstorage.controller.entities.LoadFileBody;
import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.repository.UserFileInfoRepository;
import com.example.cloudstorage.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.cloudstorage.controller.ParamsChecker.checkShouldBeNotEmptyUser;

import java.io.IOException;
import java.util.List;

@Service
public class DefaultFileService implements FileService {
    private final UserFileInfoRepository userFileInfoRepository;
    private final UserInfoRepository userInfoRepository;
    private static final Logger log = LoggerFactory.getLogger(DefaultFileService.class);

    @Autowired
    public DefaultFileService(UserFileInfoRepository userFileInfoRepository, UserInfoRepository userInfoRepository) {
        this.userFileInfoRepository = userFileInfoRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public void uploadFile(LoadFileBody loadFileBody, String fileName, String userName)
            throws IOException, IllegalAccessException {
        log.debug("uploadFile: userName = " + userName + ", fileName = " + fileName);

        UserInfo user = userInfoRepository.getByLogin(userName);
        checkShouldBeNotEmptyUser(user);

        for (UserFileInfo data : user.getListFilesInfo()) {
            if (data.getFileName().equals(fileName)) {
                throw new IllegalAccessException("File name " + fileName + " already exists");
            }
        }

        UserFileInfo data = new UserFileInfo();
        data.setFileName(fileName);
        data.setData(loadFileBody.getFile().getBytes());
        data.setUser(user);

        userFileInfoRepository.save(data);
    }

    @Override
    public List<UserFileInfo> getFileListByUser(String userName, Integer limit) {
        log.debug("getFileListByUser: userName = " + userName + ", limit = " + limit);

        UserInfo user = userInfoRepository.getByLogin(userName);
        checkShouldBeNotEmptyUser(user);

        return user.getListFilesInfo();
    }

    @Override
    public void deleteFile(String userName, String fileName) {
        log.debug("deleteFile: userName = " + userName + ", fileName = " + fileName);

        UserInfo user = userInfoRepository.getByLogin(userName);
        checkShouldBeNotEmptyUser(user);

        user.getListFilesInfo().forEach(data -> {
            if (data.getFileName().equals(fileName)) {
                userFileInfoRepository.delete(data);
            }
        });
    }

    @Override
    public void changeFileName(String userName, String oldFileName, String newFileName) {
        log.debug("changeFileName: userName = " + userName + ", oldFileName = " + oldFileName + ", newFileName = " + newFileName);

        UserInfo user = userInfoRepository.getByLogin(userName);
        checkShouldBeNotEmptyUser(user);

        user.getListFilesInfo().forEach(data -> {
            if (data.getFileName().equals(oldFileName)) {
                data.setFileName(newFileName);
                userFileInfoRepository.saveAndFlush(data);
            }
        });
    }

    @Override
    public UserFileInfo getData(String userName, String fileName) {
        log.debug("getData: userName = " + userName + ", fileName = " + fileName);

        UserInfo user = userInfoRepository.getByLogin(userName);
        checkShouldBeNotEmptyUser(user);

        for (UserFileInfo data : user.getListFilesInfo()) {
            if (data.getFileName().equals(fileName)) {
                return data;
            }
        }

        throw new NullPointerException("Data not found");
    }
}
