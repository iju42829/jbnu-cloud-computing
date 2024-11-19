package com.cloudbox.backend.file.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.InputStreamSource;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadResponse {

    private InputStreamSource inputStreamSource;
    private String fileName;
}
