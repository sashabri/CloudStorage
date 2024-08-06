package com.example.cloudstorage.service;

import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.repository.UserFileInfoRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class DefaultFileServiceChangeFileNameTests {
    UserFileInfoRepository userFileInfoRepository = Mockito.mock(UserFileInfoRepository.class);
    DefaultFileService sut = new DefaultFileService(userFileInfoRepository);

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

        sut.changeFileName(user, "img1", "lll");

        String actual = uFi1.getFileName();

        Assertions.assertEquals("lll", actual);
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

        sut.changeFileName(user, "img1", "lll");

        Mockito.verify(userFileInfoRepository).saveAndFlush(uFi1);
    }
}
