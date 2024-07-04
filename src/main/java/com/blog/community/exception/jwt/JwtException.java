package com.blog.community.exception.jwt;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum JwtException {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid token"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Expired token"),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "Unsupported token"),
    EMPTY_TOKEN(HttpStatus.BAD_REQUEST, "Empty token");
    private final HttpStatus status;
    private final String message;
    JwtException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
