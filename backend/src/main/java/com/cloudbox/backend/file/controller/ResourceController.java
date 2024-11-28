package com.cloudbox.backend.file.controller;

import com.cloudbox.backend.common.argumentResolver.annotation.Login;
import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.common.exception.InvalidRequestException;
import com.cloudbox.backend.file.dto.ResourceType;
import com.cloudbox.backend.file.dto.request.ResourceMoveRequest;
import com.cloudbox.backend.file.service.interfaces.command.FileCommandService;
import com.cloudbox.backend.file.service.interfaces.command.FolderCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "리소스 관리", description = "파일 및 폴더와 같은 리소스 관리 관련 API")
public class ResourceController {

    private final FileCommandService fileCommandService;
    private final FolderCommandService folderCommandService;

    @Operation(
            summary = "리소스 이동",
            description = "파일 또는 폴더를 다른 위치로 이동합니다.\n" +
                    "요청 값에 리소스 타입(FOLDER 또는 FILE)을 지정하고,\n" +
                    "이동할 리소스 ID와 대상 리소스 ID를 포함해야 합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "자원 이동 성공")
    })
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
