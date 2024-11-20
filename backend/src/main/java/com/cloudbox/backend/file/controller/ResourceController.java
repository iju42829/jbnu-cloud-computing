package com.cloudbox.backend.file.controller;

import com.cloudbox.backend.common.argumentResolver.annotation.Login;
import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.common.exception.InvalidRequestException;
import com.cloudbox.backend.file.dto.ResourceType;
import com.cloudbox.backend.file.dto.request.ResourceMoveRequest;
import com.cloudbox.backend.file.service.interfaces.command.FileCommandService;
import com.cloudbox.backend.file.service.interfaces.command.FolderCommandService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resources")
public class ResourceController {

    private final FileCommandService fileCommandService;
    private final FolderCommandService folderCommandService;

    @PatchMapping("/move")
    public ResponseEntity<Response<?>> moveResource(@Login MemberSessionDto memberSessionDto,
                                                    @Validated @RequestBody ResourceMoveRequest resourceMoveRequest, BindingResult bindingResult) {

        if (resourceMoveRequest.getResourceType() == ResourceType.FILE) {
            fileCommandService.moveFile(memberSessionDto, resourceMoveRequest.getResourceId(), resourceMoveRequest.getTargetResourceId());
        } else if (resourceMoveRequest.getResourceType() == ResourceType.FOLDER) {
            folderCommandService.moveFolder(memberSessionDto, resourceMoveRequest.getResourceId(), resourceMoveRequest.getTargetResourceId());
        } else {
            throw new InvalidRequestException("요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "자원 이동에 성공했습니다."), HttpStatus.OK);
    }
}
