package com.blog.community.exception.board;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BoardException {
    NON_UNIQUE_RESULT(HttpStatus.INTERNAL_SERVER_ERROR, "Non unique result"),
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "Board not found with id: "),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "Invalid input"),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed"),
    NOT_MATCH_MEMBER(HttpStatus.FORBIDDEN, "Not match member");

    private final HttpStatus status;
    private final String message;
    BoardException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
