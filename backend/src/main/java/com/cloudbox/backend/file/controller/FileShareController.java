package com.cloudbox.backend.file.controller;

import com.cloudbox.backend.common.argumentResolver.annotation.Login;
import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.file.dto.request.FileShareCreateRequest;
import com.cloudbox.backend.file.dto.response.*;
import com.cloudbox.backend.file.service.interfaces.command.FileShareCommandService;
import com.cloudbox.backend.file.service.interfaces.query.FileQueryService;
import com.cloudbox.backend.file.service.interfaces.query.FileShareQueryService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/fileShare")
@RestController
@RequiredArgsConstructor
public class FileShareController {

    private final FileShareCommandService fileShareCommandService;
    private final FileShareQueryService fileShareQueryService;
    private final FileQueryService fileQueryService;

    @Value("${domain.server-url}")
    private String url;

    @GetMapping
    public ResponseEntity<Response<FileShareResponseList>> retrieveFileShareList(@Login MemberSessionDto memberSessionDto) {
        FileShareResponseList sharedFiles = fileShareQueryService.getSharedFiles(memberSessionDto);

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "공유 파일 목록 조회에 성공했습니다.", sharedFiles), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response<FileShareCreateResponse>> addFileShare(@Login MemberSessionDto memberSessionDto,
                                                                         @RequestBody FileShareCreateRequest fileShareCreateRequest) {

        String uuid = fileShareCommandService.createFileShare(memberSessionDto, fileShareCreateRequest);
        String shareLink = url + "/fileShare/" + fileShareCreateRequest.getFileId() + "/" + uuid;

        FileShareCreateResponse fileShareCreateResponse = new FileShareCreateResponse(shareLink);

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_CREATED, "파일 공유에 성공했습니다.", fileShareCreateResponse), HttpStatus.CREATED);
    }

    @GetMapping("/{fileId}/{uuid}/info")
    public ResponseEntity<Response<FileResponse>> retrieveFileShareInfo(@PathVariable Long fileId, @PathVariable String uuid) {
        fileShareQueryService.validateFileShare(fileId, uuid);

        FileResponse fileResponse = fileQueryService.getFileResponsesById(fileId);

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "공유 파일 정보 조회에 성공했습니다.", fileResponse), HttpStatus.OK);
    }

    @GetMapping("/{fileId}/{uuid}/download")
    public ResponseEntity<InputStreamSource> downloadShareFile(@PathVariable Long fileId, @PathVariable String uuid) {
        fileShareQueryService.validateFileShare(fileId, uuid);

        FileDownloadResponse fileDownloadResponse = fileQueryService.downloadSharedFile(fileId);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDownloadResponse.getFileName() + "\"")
                .body(fileDownloadResponse.getInputStreamSource());
    }
}
