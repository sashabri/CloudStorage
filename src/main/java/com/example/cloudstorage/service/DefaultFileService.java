package com.example.cloudstorage.service;

import com.example.cloudstorage.controller.entities.ListItem;
import com.example.cloudstorage.controller.entities.LoadFileBody;
import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.repository.UserFileInfoRepository;
import com.example.cloudstorage.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.cloudstorage.controller.ParamsChecker.checkShouldBeNotEmptyUser;

import java.io.IOException;
import java.util.Collection;

@Service
public class DefaultFileService implements FileService {
    private final UserFileInfoRepository userFileInfoRepository;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public DefaultFileService(UserFileInfoRepository userFileInfoRepository, UserInfoRepository userInfoRepository) {
        this.userFileInfoRepository = userFileInfoRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public void uploadFile(LoadFileBody loadFileBody, String fileName, String userName)
            throws IOException, IllegalAccessException {
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
    public Collection<ListItem> getFileListByUser(String userName, Integer limit) {
        UserInfo user = userInfoRepository.getByLogin(userName);
        checkShouldBeNotEmptyUser(user);

        return user.getListFilesInfo().stream()
                .limit(limit)
                .map(data -> new ListItem(data.getFileName(), data.getData().length)).toList();
    }

    @Override
    public void deleteFile(String userName, String fileName) {
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
