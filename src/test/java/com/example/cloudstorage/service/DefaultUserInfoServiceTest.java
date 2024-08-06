package com.example.cloudstorage.service;

import com.example.cloudstorage.exception.InvalidDataException;
import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.repository.UserInfoRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class DefaultUserInfoServiceTest {
    UserInfoRepository uir = Mockito.mock(UserInfoRepository.class);
    DefaultUserInfoService sut = new DefaultUserInfoService(uir);

    //проверка, что user null
    @Test
    public void checkGetUserByNameTest() {
        Mockito.doReturn(null).when(uir).getByLogin("Masha");

        String expected = "User not found";

        InvalidDataException actual = Assertions.assertThrows(InvalidDataException.class, () -> {
            sut.getUserByName("Masha");
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }

    //проверка, что вызывается userInfoRepository.getByLogin(userName)
    @Test
    public void checkGetUserByNameTest2() throws InvalidDataException {
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        Mockito.doReturn(user).when(uir).getByLogin("Masha");

        sut.getUserByName("Masha");

        Mockito.verify(uir).getByLogin("Masha");
    }



}
