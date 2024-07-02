package com.blog.community.controller;

import com.blog.community.exception.member.CustomMemberException;
import com.blog.community.utils.ResponseUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler
    public ResponseEntity<?> memberExceptionHandler(CustomMemberException e) {
        return ResponseUtils.createResponse(e.getMessage(), e.getStatus());
    }
}
