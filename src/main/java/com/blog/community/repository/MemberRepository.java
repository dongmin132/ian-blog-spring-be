package com.blog.community.repository;

import com.blog.community.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository {
    public Optional<MemberEntity> findByMemberId(Long memberId);

    Optional<MemberEntity> findByEmail(String email);

    void save(MemberEntity memberEntity);
}
