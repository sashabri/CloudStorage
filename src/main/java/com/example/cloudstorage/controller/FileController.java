package com.example.cloudstorage.controller;

import com.example.cloudstorage.controller.entities.EditFileNameBody;
import com.example.cloudstorage.controller.entities.ListItem;
import com.example.cloudstorage.controller.entities.LoadFileBody;
import com.example.cloudstorage.exception.InputParamIsNullException;
import com.example.cloudstorage.exception.InvalidLoadFileBodyException;
import com.example.cloudstorage.exception.UserIsNullException;
import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.exception.InvalidDataException;
import com.example.cloudstorage.model.UserInfo;
import com.example.cloudstorage.service.FileService;
import com.example.cloudstorage.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

import static com.example.cloudstorage.controller.ParamsChecker.*;

@RestController
@RequestMapping("/")
public class FileController {
    private final FileService fileService;
    private final UserInfoService userInfoService;
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    public FileController(FileService fileService, UserInfoService userInfoService) {
        this.fileService = fileService;
        this.userInfoService = userInfoService;
    }

    @PostMapping(path = "file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void uploadFileToServer(
            @RequestParam(value = "filename") String fileName,
            @ModelAttribute LoadFileBody loadFileBody
            ) throws InvalidDataException, IOException, IllegalAccessException, InvalidLoadFileBodyException, UserIsNullException {
        if (fileName == null || fileName.isEmpty()) {
            String customFileName = generateFileName();
            fileName = customFileName;
            log.info("fileName not specified by user, fileName applied - " + customFileName);
        }

        log.debug("checkShouldBeNotEmptyLoadFileBody: input param - loadFileBody - " + loadFileBody);
        checkShouldBeNotEmptyLoadFileBody(loadFileBody);

        String userName = getUserName();
        UserInfo user = userInfoService.getUserByName(userName);

        fileService.uploadFile(loadFileBody, fileName, user);
    }

    @DeleteMapping("file")
    public ResponseEntity<Object> deleteFile(
            @RequestParam(value = "filename") String fileName
    ) throws InvalidDataException, InputParamIsNullException, UserIsNullException {
        log.debug("checkShouldBeNotEmptyStr: input param - fileName - " + fileName);
        checkShouldBeNotEmptyStr(fileName);

        String userName = getUserName();
        UserInfo user = userInfoService.getUserByName(userName);

        fileService.deleteFile(user, fileName);

        return ResponseEntity.ok().body(null);
    }

    @GetMapping(path = "file")
    public ResponseEntity<byte[]> downloadFileFromServer(
            @RequestParam(value = "filename") String fileName
    ) throws InvalidDataException, InputParamIsNullException, UserIsNullException {
        log.debug("checkShouldBeNotEmptyStr: input param - fileName - " + fileName);
        checkShouldBeNotEmptyStr(fileName);

        String userName = getUserName();
        UserInfo user = userInfoService.getUserByName(userName);

        UserFileInfo data = fileService.getData(user, fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + data.getFileName() + "\"")
                .body(data.getData());
    }

    @PutMapping("file")
    public ResponseEntity<Object> editFileName(
            @RequestParam(value = "filename") String fileName,
            @RequestBody EditFileNameBody body 
    ) throws InvalidDataException, InputParamIsNullException, UserIsNullException {
        log.debug("checkShouldBeNotEmptyStr: input param - fileName - " + fileName);
        checkShouldBeNotEmptyStr(fileName);

        log.debug("checkShouldBeNotEmptyStr: input param - EditFileNameBody - " + body.getFileName());
        checkShouldBeNotEmptyStr(body.getFileName());

        String userName = getUserName();
        UserInfo user = userInfoService.getUserByName(userName);

        fileService.changeFileName(user, fileName, body.getFileName());
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("list")
    public Collection<ListItem> getListFiles(
            @RequestParam Integer limit
    ) throws InvalidDataException, UserIsNullException, InputParamIsNullException {
        log.debug("checkShouldBeNotEmptyNumber: input param - limit - " + limit);
        checkShouldBeNotEmptyNumber(limit);

        String userName = getUserName();
        UserInfo user = userInfoService.getUserByName(userName);

        return user.getListFilesInfo().stream()
                .limit(limit)
                .map(data -> new ListItem(data.getFileName(), data.getData().length)).toList();
    }

    private String generateFileName() {
        return "New File " + java.time.LocalDateTime.now();
    }
    private String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

