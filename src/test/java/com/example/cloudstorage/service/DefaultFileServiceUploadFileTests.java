package com.example.cloudstorage.service;

import com.example.cloudstorage.controller.entities.LoadFileBody;
import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.repository.UserFileInfoRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultFileServiceUploadFileTests {
    UserFileInfoRepository userFileInfoRepository = Mockito.mock(UserFileInfoRepository.class);
    DefaultFileService sut = new DefaultFileService(userFileInfoRepository);


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

        sut.uploadFile(lFb, "data", user);

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

        String expected = "File name img, already exists";

        IllegalAccessException actual = Assertions.assertThrows(IllegalAccessException.class, () -> {
                    sut.uploadFile(lFb, "img", user);
                }
        );

        Assertions.assertEquals(expected, actual.getMessage());
    }
}
