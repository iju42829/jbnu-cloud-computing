package com.cloudbox.backend.file.controller;

import com.cloudbox.backend.common.argumentResolver.annotation.Login;
import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.file.dto.request.FolderCreateRequest;
import com.cloudbox.backend.file.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/folder")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/{parentFolderId}")
    public ResponseEntity<Response<?>> addFolder(@PathVariable Long parentFolderId,
                                                 @RequestBody FolderCreateRequest folderCreateRequest,
                                                 @Login MemberSessionDto memberSessionDto) {

        Long folder = folderService.createFolder(parentFolderId, folderCreateRequest, memberSessionDto);

        return new ResponseEntity<>(Response.createResponseWithoutData(200, "폴더 생성 성공"), HttpStatus.OK);
    }
}
