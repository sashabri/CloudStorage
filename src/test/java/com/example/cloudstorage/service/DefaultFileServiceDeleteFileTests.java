package com.example.cloudstorage.service;

import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.repository.UserFileInfoRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class DefaultFileServiceDeleteFileTests {
    UserFileInfoRepository userFileInfoRepository = Mockito.mock(UserFileInfoRepository.class);
    DefaultFileService sut = new DefaultFileService(userFileInfoRepository);

    // вызывается ли делит у репозитри userFileInfoRepository.delete
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
        UserFileInfo uFi2 = new UserFileInfo();
        uFi2.setFileName("img2");
        uFi2.setData(new byte[2145]);
        UserFileInfo uFi3 = new UserFileInfo();
        uFi3.setFileName("img3");
        uFi3.setData(new byte[2196]);

        list.add(uFi1);
        list.add(uFi2);
        list.add(uFi3);

        user.setListFilesInfo(list);

        sut.deleteFile(user, "img3");

        Mockito.verify(userFileInfoRepository).delete(uFi3);
    }
}
