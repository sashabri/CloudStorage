package com.example.cloudstorage.service;

import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.repository.UserFileInfoRepository;
import com.example.cloudstorage.repository.UserInfoRepository;
import com.example.cloudstorage.service.DefaultFileService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class DefaultFileServiceGetDataTests {
    UserFileInfoRepository userFileInfoRepository = Mockito.mock(UserFileInfoRepository.class);
    UserInfoRepository userInfoRepository = Mockito.mock(UserInfoRepository.class);
    DefaultFileService sut = new DefaultFileService(userFileInfoRepository, userInfoRepository);

    //проверка, что user null
    @Test
    public void checkUserNullInGetDataTest() {
        Mockito.doReturn(null).when(userInfoRepository).getByLogin("Masha");

        String expected = "User not found";

        NullPointerException actual = Assertions.assertThrows(NullPointerException.class, () -> {
            sut.getData("Masha", "xxx");
        });

        Assertions.assertEquals(expected, actual.getMessage());
    }

    //данные не найдены
    @Test
    public void checkValueNotExistInGetDataTest() {
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        List<UserFileInfo> list = new ArrayList<>();
        user.setListFilesInfo(list);

        Mockito.doReturn(user).when(userInfoRepository).getByLogin("Masha");

        String expected = "Data not found";

        NullPointerException actual = Assertions.assertThrows(NullPointerException.class, () -> {
                    sut.getData("Masha", "img");
                }
        );

        Assertions.assertEquals(expected, actual.getMessage());
    }

    //возвращаемое значение
    @Test
    public void checkReturnValueInGetDataTest() {
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        List<UserFileInfo> list = new ArrayList<>();

        UserFileInfo expected = new UserFileInfo();
        expected.setFileName("img");

        list.add(expected);

        user.setListFilesInfo(list);

        Mockito.doReturn(user).when(userInfoRepository).getByLogin("Masha");

        UserFileInfo actual =  sut.getData("Masha", "img");

        Assertions.assertEquals(expected, actual);
    }
}
