package com.blog.community.exception.member;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberException {
    NOT_FOUND(HttpStatus.NOT_FOUND, "Member not found with id: "),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "Member already exists with email: "),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "Member already exists with nickname: "),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Invalid password"),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "Invalid email"),
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST, "Invalid nickname"),
    INVALID_FILE(HttpStatus.BAD_REQUEST, "Invalid file");

    private final HttpStatus status;
    private final String message;
    MemberException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
