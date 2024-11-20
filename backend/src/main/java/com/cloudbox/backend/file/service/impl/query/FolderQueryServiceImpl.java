package com.cloudbox.backend.file.service.impl.query;

import com.cloudbox.backend.common.dto.MemberSessionDto;
import com.cloudbox.backend.file.domain.Folder;
import com.cloudbox.backend.file.dto.response.FolderResponse;
import com.cloudbox.backend.file.exception.FolderNotFoundException;
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
                .orElseThrow(FolderNotFoundException::new);

        return folder.buildFullFolderPath();
    }

    @Override
    public List<FolderResponse> getFolderResponseById(Long folderId, MemberSessionDto memberSessionDto) {
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
}
