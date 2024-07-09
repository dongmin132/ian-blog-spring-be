package com.blog.community.service;

import com.blog.community.dto.member.request.JoinDto;
import com.blog.community.dto.member.request.MemberUpdateRequest;
import com.blog.community.dto.member.response.MemberResponse;
import com.blog.community.dto.member.response.ProfileUpdateResponse;

public interface MemberService {
    void save(JoinDto joinDto);

    void isEmailExists(String email);

    void isNicknameExists(String nickname);

    MemberResponse getMember(Long memberId);

    void updateMember(Long memberId, MemberUpdateRequest memberUpdateRequest);

    void updatePassword(Long memberId, String password);

    void deleteMember(Long memberId);
}

