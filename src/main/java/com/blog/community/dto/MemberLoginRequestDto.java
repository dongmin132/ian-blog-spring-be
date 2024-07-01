package com.blog.community.dto;

import lombok.Getter;

@Getter
public class MemberLoginRequestDto {
    private final String email;
    private final String password;

    public MemberLoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
