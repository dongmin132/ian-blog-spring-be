package com.blog.community.exception.jwt;

import org.springframework.http.HttpStatus;

public class CustomJwtException extends RuntimeException {
    private final JwtException jwtException;
    public CustomJwtException(JwtException jwtException) {
        super(jwtException.getMessage());
        this.jwtException = jwtException;
    }

    public HttpStatus getStatus() {
        return jwtException.getStatus();
    }

    public String getMessage() {
        return jwtException.getMessage();
    }

}
