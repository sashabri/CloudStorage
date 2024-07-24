package com.example.cloudstorage.service;

import com.example.cloudstorage.controller.entities.LoadFileBody;
import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.repository.UserFileInfoRepository;
import com.example.cloudstorage.repository.UserInfoRepository;
import com.example.cloudstorage.service.DefaultFileService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultFileServiceUploadFileTests {
    UserFileInfoRepository userFileInfoRepository = Mockito.mock(UserFileInfoRepository.class);
    UserInfoRepository userInfoRepository = Mockito.mock(UserInfoRepository.class);
    DefaultFileService sut = new DefaultFileService(userFileInfoRepository, userInfoRepository);

    //проверка, что user null
    @Test
    public void checkUserNullInUploadFileTest() {
        Mockito.doReturn(null).when(userInfoRepository).getByLogin("Masha");

        String expected = "User not found";

        NullPointerException actual = Assertions.assertThrows(NullPointerException.class, () -> {
            sut.uploadFile(null, null, "Masha");
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }

    //проверка, что вызывается userInfoRepository.getByLogin(userName)
    @Test
    public void checkGetByLoginByUserInfoRepoInUploadFileTest() throws IOException, IllegalAccessException {
        MockMultipartFile file = new MockMultipartFile(
                "data",
                "filename.txt",
                "text/plain",
                "some xml".getBytes()
        );
        LoadFileBody lFb = new LoadFileBody("123456", file);

        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        List<UserFileInfo> list = new ArrayList<>();
        user.setListFilesInfo(list);

        Mockito.doReturn(user).when(userInfoRepository).getByLogin("Masha");

        sut.uploadFile(lFb, "img", "Masha");

        Mockito.verify(userInfoRepository).getByLogin("Masha");
    }

    //проверка, что вызывается userFileInfoRepository.save(data)
    @Test
    public void checkSaveByUserFileInfoRepoInUploadFileTest() throws IOException, IllegalAccessException {
        MockMultipartFile file = new MockMultipartFile(
                "data",
                "filename.txt",
                "text/plain",
                "some xml".getBytes()
        );
        LoadFileBody lFb = new LoadFileBody("123456", file);

        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        List<UserFileInfo> list = new ArrayList<>();
        user.setListFilesInfo(list);

        Mockito.doReturn(user).when(userInfoRepository).getByLogin("Masha");

        sut.uploadFile(lFb, "data", "Masha");

        UserFileInfo data = new UserFileInfo();
        data.setFileName("data");
        data.setData(lFb.getFile().getBytes());
        data.setUser(user);

        Mockito.verify(userFileInfoRepository).save(data);
    }

    //проверка, что файл уже сужествует
    @Test
    public void checkFileExistInUploadFileTest() {
        LoadFileBody lFb = new LoadFileBody("123456", null);

        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        List<UserFileInfo> list = new ArrayList<>();
        UserFileInfo uFi = new UserFileInfo();
        uFi.setFileName("img");
        list.add(uFi);

        user.setListFilesInfo(list);

        Mockito.doReturn(user).when(userInfoRepository).getByLogin("Masha");

        String expected = "File name img already exists";

        IllegalAccessException actual = Assertions.assertThrows(IllegalAccessException.class, () -> {
                    sut.uploadFile(lFb, "img", "Masha");
                }
        );

        Assertions.assertEquals(expected, actual.getMessage());
    }
}
