package com.example.cloudstorage.service;

import com.example.cloudstorage.exception.InvalidDataException;
import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.repository.UserFileInfoRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class DefaultFileServiceGetDataTests {
    UserFileInfoRepository userFileInfoRepository = Mockito.mock(UserFileInfoRepository.class);
    DefaultFileService sut = new DefaultFileService(userFileInfoRepository);

    //данные не найдены
    @Test
    public void checkValueNotExistInGetDataTest() {
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        List<UserFileInfo> list = new ArrayList<>();
        user.setListFilesInfo(list);

        String expected = "Data not found";

        InvalidDataException actual = Assertions.assertThrows(InvalidDataException.class, () -> {
                    sut.getData(user, "img");
                }
        );

        Assertions.assertEquals(expected, actual.getMessage());
    }

    //возвращаемое значение
    @Test
    public void checkReturnValueInGetDataTest() throws InvalidDataException {
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setLogin("Masha");
        user.setPassword("123abc");

        List<UserFileInfo> list = new ArrayList<>();

        UserFileInfo expected = new UserFileInfo();
        expected.setFileName("img");

        list.add(expected);

        user.setListFilesInfo(list);

        UserFileInfo actual =  sut.getData(user, "img");

        Assertions.assertEquals(expected, actual);
    }
}
