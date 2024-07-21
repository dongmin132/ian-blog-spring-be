package com.blog.community.exception.file;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FileException {
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "File not found"),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed");

    private final HttpStatus status;
    private final String message;
    FileException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
