package com.blog.community.exception.member;

import org.springframework.http.HttpStatus;

public class CustomMemberException extends RuntimeException {
    private final MemberException memberException;
    public CustomMemberException(MemberException memberException) {
        super(memberException.getMessage());
        this.memberException = memberException;
    }

    public HttpStatus getStatus() {
        return memberException.getStatus();
    }

    public String getMessage() {
        return memberException.getMessage();
    }

}
