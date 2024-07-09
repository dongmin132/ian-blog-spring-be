package com.blog.community.dto.member.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProfileUpdateResponse {
    private final String memberNickname;
    private final String memberProfileImage;

    //from 엔티티 -> DTO 정적 메서드
    public static ProfileUpdateResponse from(String memberNickname,String memberProfileImage) {
        return new ProfileUpdateResponse(memberNickname,memberProfileImage);
    }
}
