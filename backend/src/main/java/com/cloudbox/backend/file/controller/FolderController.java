package com.cloudbox.backend.file.controller;

import com.cloudbox.backend.common.argumentResolver.annotation.Login;
import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.common.exception.InvalidRequestException;
import com.cloudbox.backend.file.dto.ResourceType;
import com.cloudbox.backend.file.dto.request.FolderCreateRequest;
import com.cloudbox.backend.file.dto.response.FolderMoveOptionsResponse;
import com.cloudbox.backend.file.service.interfaces.command.FolderCommandService;
import com.cloudbox.backend.file.service.interfaces.query.FolderQueryService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
@Tag(name = "폴더 관리", description = "폴더 관련 API")
public class FolderController {

    private final FolderCommandService folderCommandService;
    private final FolderQueryService folderQueryService;

    @Operation(summary = "폴더 생성", description = "지정된 상위 폴더에 새로운 폴더를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "폴더 생성 성공"),
    })
    @PostMapping("/{parentFolderId}")
    public ResponseEntity<Response<?>> addFolder(@PathVariable Long parentFolderId,
                                                 @Login MemberSessionDto memberSessionDto,
                                                 @Validated @RequestBody FolderCreateRequest folderCreateRequest,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("폴더 생성 요청 값이 올바르지 않습니다. 다시 확인해 주세요.", bindingResult);
        }

        Long folder = folderCommandService.createFolder(parentFolderId, folderCreateRequest, memberSessionDto);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_CREATED, "폴더 생성 성공"), HttpStatus.CREATED);
    }

    @GetMapping("/moveList")
    public ResponseEntity<Response<FolderMoveOptionsResponse>> retrieveFolderMoveList(@Login MemberSessionDto memberSessionDto,
                                                                                      @RequestParam Long moveId,
                                                                                      @RequestParam Long folderId,
                                                                                      @RequestParam ResourceType resourceType) {

        FolderMoveOptionsResponse folderResponses = folderQueryService.getFolderResponsesWithoutMoveId(folderId, moveId, resourceType, memberSessionDto);

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "폴더 또는 파일 이동목록 조회에 성공했습니다.", folderResponses), HttpStatus.OK);
    }
}
