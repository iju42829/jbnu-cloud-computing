package com.cloudbox.backend.file.controller;

import com.cloudbox.backend.common.argumentResolver.annotation.Login;
import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.file.service.S3StorageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileController {

    private final S3StorageService s3StorageService;

    @PostMapping("/file")
    public ResponseEntity<Response<?>> upload(@RequestParam("file") MultipartFile uploadFile,
                                              @RequestParam(required = false) Long folderId,
                                              @Login MemberSessionDto memberSessionDto) {

        s3StorageService.fileUpload(memberSessionDto, uploadFile, folderId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "파일 업로드에 성공하였습니다."), HttpStatus.OK);
    }
}
