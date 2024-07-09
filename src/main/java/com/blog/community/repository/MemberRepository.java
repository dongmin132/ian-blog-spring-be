package com.blog.community.repository;

import com.blog.community.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository {
    Optional<MemberEntity> findByMemberId(Long memberId);

    Optional<MemberEntity> findByEmail(String email);

    void save(MemberEntity memberEntity);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    void updateMemberProfileImage(Long memberId, String nickname, String profileImage);

    void updatePassword(Long memberId, String encodedPassword);

    void deleteMember(Long memberId);
}
