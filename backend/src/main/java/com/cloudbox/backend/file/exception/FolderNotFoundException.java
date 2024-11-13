package com.cloudbox.backend.file.exception;

public class FolderNotFoundException extends RuntimeException {
    public FolderNotFoundException() {
        super();
    }

    public FolderNotFoundException(String message) {
        super(message);
    }

    public FolderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FolderNotFoundException(Throwable cause) {
        super(cause);
    }
}
