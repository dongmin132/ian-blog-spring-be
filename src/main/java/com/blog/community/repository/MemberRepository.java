package com.blog.community.repository;

import com.blog.community.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository {
    public Optional<MemberEntity> findById(Long memberId);
}
