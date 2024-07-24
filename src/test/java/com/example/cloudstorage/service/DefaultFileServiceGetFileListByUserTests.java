package com.example.cloudstorage.service;

import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.repository.UserFileInfoRepository;
import com.example.cloudstorage.repository.UserInfoRepository;
import com.example.cloudstorage.service.DefaultFileService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultFileServiceGetFileListByUserTests {
    UserFileInfoRepository userFileInfoRepository = Mockito.mock(UserFileInfoRepository.class);
    UserInfoRepository userInfoRepository = Mockito.mock(UserInfoRepository.class);
    DefaultFileService sut = new DefaultFileService(userFileInfoRepository, userInfoRepository);

    //проверка что user null
    @Test
    public void checkUserNullInGetFileListByUserTest() {
        Mockito.doReturn(null).when(userInfoRepository).getByLogin("Masha");

        String expected = "User not found";

        NullPointerException actual = Assertions.assertThrows(NullPointerException.class, () -> {
            sut.getFileListByUser("Masha", null);
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }

    //проверка, что вызывается userInfoRepository.getByLogin(userName)
    @Test
    public void checkGetByLoginByUserInfoRepoInGetFileListByUserTest() throws IOException, IllegalAccessException {
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        List<UserFileInfo> list = new ArrayList<>();
        user.setListFilesInfo(list);

        Mockito.doReturn(user).when(userInfoRepository).getByLogin("Masha");

        sut.getFileListByUser("Masha", 1);

        Mockito.verify(userInfoRepository).getByLogin("Masha");
    }

    //проверка, что количество элементов коллекции соответствует лимиту
    @Test
    public void checkLimitInGetFileListByUserTest() {
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        List<UserFileInfo> list = new ArrayList<>();

        UserFileInfo uFi1 = new UserFileInfo();
        uFi1.setFileName("img1");
        uFi1.setData(new byte[2158]);
        UserFileInfo uFi2 = new UserFileInfo();
        uFi1.setFileName("img2");
        uFi2.setData(new byte[2145]);
        UserFileInfo uFi3 = new UserFileInfo();
        uFi1.setFileName("img3");
        uFi3.setData(new byte[2196]);

        list.add(uFi1);
        list.add(uFi2);
        list.add(uFi3);

        user.setListFilesInfo(list);

        Mockito.doReturn(user).when(userInfoRepository).getByLogin("Masha");

        Integer actual = sut.getFileListByUser("Masha", 2).size();

        Assertions.assertEquals(2, actual);
    }
}
