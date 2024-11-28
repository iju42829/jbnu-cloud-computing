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
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "이동 목록 조회", description = "지정된 리소스를 이동할 수 있는 폴더 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이동 가능한 폴더 목록 조회 성공"),
    })
    @GetMapping("/moveList")
    public ResponseEntity<Response<FolderMoveOptionsResponse>> retrieveFolderMoveList(@Login MemberSessionDto memberSessionDto,
                                                                                      @Parameter(description = "이동할 폴더 또는 파일 ID") @RequestParam Long moveId,
                                                                                      @Parameter(description = "현재 폴더 ID (처음 시작 시 ROOT 폴더 ID로 지정하고, 이동할 폴더 ID를 지정합니다.)") @RequestParam Long folderId,
                                                                                      @Parameter(description = "리소스 타입 (FOLDER 또는 FILE)") @RequestParam ResourceType resourceType) {

        FolderMoveOptionsResponse folderResponses = folderQueryService.getFolderResponsesWithoutMoveId(folderId, moveId, resourceType, memberSessionDto);

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "폴더 또는 파일 이동목록 조회에 성공했습니다.", folderResponses), HttpStatus.OK);
    }

    @Operation(summary = "폴더 삭제", description = "지정된 폴더와 해당 폴더에 포함된 하위 리소스를 삭제합니다.\n" + "삭제하려는 폴더의 ID를 경로 변수로 전달해야 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "폴더 삭제 성공")
    })
    @DeleteMapping("/{folderId}")
    public ResponseEntity<Response<?>> removeFolder(@Login MemberSessionDto memberSessionDto,
                                                    @Parameter(description = "삭제할 폴더의 ID") @PathVariable Long folderId) {
        folderCommandService.deleteFolder(memberSessionDto, folderId);

        return new ResponseEntity<>(Response.createResponseWithoutData(HttpServletResponse.SC_OK, "폴더와 하위 리소스들을 삭제하였습니다."), HttpStatus.OK);
    }
}
