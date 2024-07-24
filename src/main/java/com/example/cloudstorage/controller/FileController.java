package com.example.cloudstorage.controller;

import com.example.cloudstorage.controller.entities.EditFileNameBody;
import com.example.cloudstorage.controller.entities.ListItem;
import com.example.cloudstorage.controller.entities.LoadFileBody;
import com.example.cloudstorage.model.UserFileInfo;
import com.example.cloudstorage.exception.InternalServerErrorException;
import com.example.cloudstorage.exception.InvalidDataException;
import com.example.cloudstorage.exception.UnauthorisedException;
import com.example.cloudstorage.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.example.cloudstorage.controller.ParamsChecker.checkShouldBeNotEmptyStr;
import static com.example.cloudstorage.controller.ParamsChecker.checkShouldBeNotEmptyLoadFileBody;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(path = "file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void uploadFileToServer(
            @RequestParam(value = "filename") String fileName,
            @ModelAttribute LoadFileBody loadFileBody
            ) throws InvalidDataException, UnauthorisedException, IOException, IllegalAccessException {
        if (fileName == null || fileName.isEmpty()) {
            fileName = generateFileName();
        }

        checkShouldBeNotEmptyLoadFileBody(loadFileBody);
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        fileService.uploadFile(loadFileBody, fileName, userName);
    }

    @DeleteMapping("file")
    public ResponseEntity<Object> deleteFile(
            @RequestParam(value = "filename") String fileName
    ) throws InvalidDataException, UnauthorisedException, InternalServerErrorException {
        checkShouldBeNotEmptyStr(fileName);
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        fileService.deleteFile(userName, fileName);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(path = "file")
    public ResponseEntity<byte[]> downloadFileFromServer(
            @RequestParam(value = "filename") String fileName
    ) throws InvalidDataException, UnauthorisedException, InternalServerErrorException {
        checkShouldBeNotEmptyStr(fileName);
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserFileInfo data = fileService.getData(userName, fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + data.getFileName() + "\"")
                .body(data.getData());
    }

    @PutMapping("file")
    public ResponseEntity<Object> editFileName(
            @RequestParam(value = "filename") String fileName,
            @RequestBody EditFileNameBody body 
    ) throws InvalidDataException, UnauthorisedException, InternalServerErrorException {
        checkShouldBeNotEmptyStr(body.getFileName());
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        fileService.changeFileName(userName, fileName, body.getFileName());
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("list")
    public Collection<ListItem> getListFiles(
            @RequestParam Integer limit
    ) throws InvalidDataException, UnauthorisedException, InternalServerErrorException {
        // todo проверка limit
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return fileService.getFileListByUser(userName, limit);
    }

    private String generateFileName() {
        return "New File " + java.time.LocalDateTime.now();
    }
}
