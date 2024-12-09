package com.cloudbox.backend.file.service.impl.query;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.Folder;
import com.cloudbox.backend.file.dto.FolderType;
import com.cloudbox.backend.file.dto.ResourceType;
import com.cloudbox.backend.file.dto.response.FolderMoveOptionsResponse;
import com.cloudbox.backend.file.dto.response.FolderResponse;
import com.cloudbox.backend.file.exception.FolderNotFoundException;
import com.cloudbox.backend.file.exception.RootFolderMoveException;
import com.cloudbox.backend.file.repository.FolderRepository;
import com.cloudbox.backend.file.service.interfaces.query.FolderQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FolderQueryServiceImpl implements FolderQueryService {

    private final FolderRepository folderRepository;

    @Override
    public String getFullFolderPathById(Long folderId) {
        Folder folder = folderRepository
                .findById(folderId)
                .orElseThrow(() -> new FolderNotFoundException("해당 폴더를 찾을 수 없습니다."));

        return folder.buildFullFolderPath();
    }

    @Override
    public List<FolderResponse> getFolderResponsesById(Long folderId, MemberSessionDto memberSessionDto) {
        Folder folder = getFolderEntityByIdAndCreateBy(folderId, memberSessionDto);

        List<Folder> childFolders = folder.getChildFolders();

        return childFolders.stream()
                .map(FolderResponse::fromFolder)
                .toList();
    }

    @Override
    public Folder getFolderEntityByIdAndCreateBy(Long folderId, MemberSessionDto memberSessionDto) {
        Folder folder = folderRepository.findByIdAndCreateBy(folderId, memberSessionDto.getUsername()).orElse(null);

        if (folder == null) {
            folder = folderRepository.findById(folderId).orElseThrow(FolderNotFoundException::new);

            if (!folder.getMember().getUsername().equals(memberSessionDto.getUsername())) {
                throw new FolderNotFoundException("해당 폴더에 접근 권한이 없습니다.");
            }
        }

        return folder;
    }

    @Override
    public FolderMoveOptionsResponse getFolderResponsesWithoutMoveId(Long currentFolderId, Long moveId, ResourceType resourceType, MemberSessionDto memberSessionDto) {
        Folder folder = getFolderEntityByIdAndCreateBy(currentFolderId, memberSessionDto);

        validateRootFolder(moveId, resourceType, memberSessionDto);

        List<FolderResponse> folderResponses = getChildFoldersForMove(folder, moveId, resourceType);

        String fullFolderPath = folder.buildFullFolderPath();

        Long parentFolderId = null;
        if (folder.getFolderType() != FolderType.ROOT) {
            parentFolderId = folder.getParentFolder().getId();
        }

        return new FolderMoveOptionsResponse(fullFolderPath, moveId, parentFolderId, currentFolderId, folderResponses);
    }

    private void validateRootFolder(Long moveId, ResourceType resourceType, MemberSessionDto memberSessionDto) {
        if (resourceType == ResourceType.FOLDER) {
            Folder moveFolder = getFolderEntityByIdAndCreateBy(moveId, memberSessionDto);
            if (moveFolder.getFolderType() == FolderType.ROOT) {
                throw new RootFolderMoveException("루트 폴더는 이동할 수 없습니다.");
            }
        }
    }

    private List<FolderResponse> getChildFoldersForMove(Folder folder, Long moveId, ResourceType resourceType) {
        return folder.getChildFolders().stream()
                .filter(childFolder -> resourceType != ResourceType.FOLDER || !childFolder.getId().equals(moveId))
                .map(FolderResponse::fromFolder)
                .toList();
    }
}
