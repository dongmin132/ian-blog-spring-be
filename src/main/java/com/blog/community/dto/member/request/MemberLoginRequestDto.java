package com.blog.community.dto.member.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberLoginRequestDto {
    private final String email;
    private final String password;
}
