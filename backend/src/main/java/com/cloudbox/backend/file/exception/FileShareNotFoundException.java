package com.cloudbox.backend.file.exception;

public class FileShareNotFoundException extends RuntimeException {
    public FileShareNotFoundException() {
        super();
    }

    public FileShareNotFoundException(String message) {
        super(message);
    }

    public FileShareNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileShareNotFoundException(Throwable cause) {
        super(cause);
    }
}
