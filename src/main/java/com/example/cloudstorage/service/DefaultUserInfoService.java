package com.example.cloudstorage.service;

import com.example.cloudstorage.exception.InvalidDataException;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.repository.UserInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.example.cloudstorage.controller.ParamsChecker.checkShouldBeNotEmptyUser;

@Service
public class DefaultUserInfoService implements UserInfoService {
    private static final Logger log = LoggerFactory.getLogger(DefaultFileService.class);
    private final UserInfoRepository userInfoRepository;

    public DefaultUserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserInfo getUserByName(String userName) throws InvalidDataException {
        log.debug("getUserByName: userName = " + userName);
        UserInfo user = userInfoRepository.getByLogin(userName);
        checkShouldBeNotEmptyUser(user);
        return user;
    }
}
