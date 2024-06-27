package com.blog.community.service;

import com.blog.community.entity.MemberEntity;
import com.blog.community.exception.MemberNotFoundException;
import com.blog.community.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void findById(Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));;
        System.out.println(memberEntity);
    }
}
