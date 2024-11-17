package com.cloudbox.backend.file.controller;

import com.cloudbox.backend.common.argumentResolver.annotation.Login;
import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.file.dto.response.FileResponse;
import com.cloudbox.backend.file.dto.response.FolderResponse;
import com.cloudbox.backend.file.dto.response.MainPageResponse;
import com.cloudbox.backend.file.service.FileService;
import com.cloudbox.backend.file.service.FolderService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/main")
@RestController
@RequiredArgsConstructor
public class MainController {

    private final FileService fileService;
    private final FolderService folderService;

    @GetMapping
    public ResponseEntity<Response<MainPageResponse>> mainPage(@RequestParam(required = false) Long folderId, @Login MemberSessionDto memberSessionDto) {
        List<FileResponse> fileResponses = fileService.getFileResponsesByFolder(folderId, memberSessionDto);
        List<FolderResponse> folderResponses = folderService.getFolderResponseById(folderId, memberSessionDto);

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "파일과 폴더 조회에 성공했습니다.", new MainPageResponse(folderResponses, fileResponses)), HttpStatus.OK);
    }
}
