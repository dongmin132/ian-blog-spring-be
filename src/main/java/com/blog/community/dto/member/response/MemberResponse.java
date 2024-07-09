package com.blog.community.dto.member.response;

import com.blog.community.entity.MemberEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberResponse {
    private final Long memberId;
    private final String memberEmail;
    private final String memberNickname;
    private final String memberProfileImage;

    public static MemberResponse from(MemberEntity entity) {
        return new MemberResponse(entity.getMemberId(), entity.getMemberEmail(), entity.getMemberNickname(), entity.getMemberProfileImage());
    }
}
