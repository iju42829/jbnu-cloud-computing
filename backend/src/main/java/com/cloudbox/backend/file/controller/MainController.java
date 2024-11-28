package com.cloudbox.backend.file.controller;

import com.cloudbox.backend.common.argumentResolver.annotation.Login;
import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.common.dto.Response;
import com.cloudbox.backend.file.dto.response.FileResponse;
import com.cloudbox.backend.file.dto.response.FolderResponse;
import com.cloudbox.backend.file.dto.response.MainPageResponse;
import com.cloudbox.backend.file.service.interfaces.query.FileQueryService;
import com.cloudbox.backend.file.service.interfaces.query.FolderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "메인 페이지", description = "메인 페이지 관련 API")
public class MainController {

    private final FileQueryService fileQueryService;
    private final FolderQueryService folderQueryService;

    @Operation(summary = "메인 페이지 조회", description = "로그인한 사용자의 루트 폴더를 기준으로 해당 폴더의 파일과 하위 폴더를 조회합니다.\n" + "루트 폴더가 아닌 다른 폴더를 조회하려면 `folderId`를 지정하십시오.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "파일과 폴더 조회 성공")
    })
    @GetMapping
    public ResponseEntity<Response<MainPageResponse>> mainPage(@Parameter(description = "조회할 폴더의 ID. 기본값은 루트 폴더입니다.") @RequestParam Long folderId,
                                                               @Login MemberSessionDto memberSessionDto) {
        List<FileResponse> fileResponses = fileQueryService.getFileResponsesByFolder(folderId, memberSessionDto);
        List<FolderResponse> folderResponses = folderQueryService.getFolderResponsesById(folderId, memberSessionDto);

        return new ResponseEntity<>(Response.createResponse(HttpServletResponse.SC_OK, "파일과 폴더 조회에 성공했습니다.", new MainPageResponse(folderResponses, fileResponses)), HttpStatus.OK);
    }
}
