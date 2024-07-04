package com.blog.community.controller;

import com.blog.community.exception.jwt.CustomJwtException;
import com.blog.community.exception.member.CustomMemberException;
import com.blog.community.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    public ResponseEntity<?> exceptionHandler(MaxUploadSizeExceededException e) {
        return ResponseUtils.createResponse(HttpStatus.PAYLOAD_TOO_LARGE,e.getMessage());
    }

    @ExceptionHandler(CustomMemberException.class)
    public ResponseEntity<?> memberExceptionHandler(CustomMemberException e) {
        return ResponseUtils.createResponse(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(CustomJwtException.class)
    public ResponseEntity<?> jwtExceptionHandler(CustomJwtException e) {
        return ResponseUtils.createResponse(e.getStatus(), e.getMessage());
    }
}
