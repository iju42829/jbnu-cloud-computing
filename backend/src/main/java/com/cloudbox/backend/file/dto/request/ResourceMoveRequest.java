package com.cloudbox.backend.file.dto.request;

import com.cloudbox.backend.file.dto.ResourceType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResourceMoveRequest {
    private Long resourceId;
    private Long targetResourceId;
    private ResourceType resourceType;

}
