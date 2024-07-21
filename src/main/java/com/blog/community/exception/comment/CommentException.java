package com.blog.community.exception.comment;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommentException {
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "Board not found"),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "Comment not found"),
    COMMENT_CREATION_FAILED(HttpStatus.BAD_REQUEST, "Comment creation failed"),
    COMMENT_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "Comment update failed"),
    COMMENT_DELETION_FAILED(HttpStatus.BAD_REQUEST, "Comment deletion failed");

    private final HttpStatus status;
    private final String message;
    CommentException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
