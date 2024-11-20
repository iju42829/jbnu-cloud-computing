package com.cloudbox.backend.file.exception;

public class RootFolderMoveException extends RuntimeException {

    public RootFolderMoveException() {
        super();
    }

    public RootFolderMoveException(String message) {
        super(message);
    }

    public RootFolderMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public RootFolderMoveException(Throwable cause) {
        super(cause);
    }
}
