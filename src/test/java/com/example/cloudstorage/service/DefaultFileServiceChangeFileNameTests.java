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

public class DefaultFileServiceChangeFileNameTests {
    UserFileInfoRepository userFileInfoRepository = Mockito.mock(UserFileInfoRepository.class);
    UserInfoRepository userInfoRepository = Mockito.mock(UserInfoRepository.class);
    DefaultFileService sut = new DefaultFileService(userFileInfoRepository, userInfoRepository);

    //проверка, что user null
    @Test
    public void checkUserNullInChangeFileNameTest() {
        Mockito.doReturn(null).when(userInfoRepository).getByLogin("Masha");

        String expected = "User not found";

        NullPointerException actual = Assertions.assertThrows(NullPointerException.class, () -> {
            sut.changeFileName("Masha", "hhh", "lll");
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }

    //проверить переименованый элемент
    @Test
    public void checkNewFileNameChangeFileNameTest() {
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        List<UserFileInfo> list = new ArrayList<>();

        UserFileInfo uFi1 = new UserFileInfo();
        uFi1.setFileName("img1");
        uFi1.setData(new byte[2158]);

        list.add(uFi1);

        user.setListFilesInfo(list);

        Mockito.doReturn(user).when(userInfoRepository).getByLogin("Masha");

        sut.changeFileName("Masha", "img1", "lll");

        String actual = uFi1.getFileName();

        Assertions.assertEquals("lll", actual);
    }

    //проверка, что вызывается userInfoRepository.getByLogin(userName)
    @Test
    public void checkGetByLoginByUserInfoRepoInNameChangeFileNameTest() throws IOException, IllegalAccessException {
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        List<UserFileInfo> list = new ArrayList<>();

        UserFileInfo uFi1 = new UserFileInfo();
        uFi1.setFileName("img1");
        uFi1.setData(new byte[2158]);

        list.add(uFi1);

        user.setListFilesInfo(list);

        Mockito.doReturn(user).when(userInfoRepository).getByLogin("Masha");

        sut.changeFileName("Masha", "img1", "lll");

        Mockito.verify(userInfoRepository).getByLogin("Masha");
    }

    // вызывается ли userFileInfoRepository.saveAndFlush(data)
    @Test
    public void checkDeleteByUserFilInfoRepoInDeleteFileTest() {
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        List<UserFileInfo> list = new ArrayList<>();

        UserFileInfo uFi1 = new UserFileInfo();
        uFi1.setFileName("img1");
        uFi1.setData(new byte[2158]);

        list.add(uFi1);

        user.setListFilesInfo(list);

        Mockito.doReturn(user).when(userInfoRepository).getByLogin("Masha");

        sut.changeFileName("Masha", "img1", "lll");

        Mockito.verify(userFileInfoRepository).saveAndFlush(uFi1);
    }
}
