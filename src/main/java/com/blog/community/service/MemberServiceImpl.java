package com.blog.community.service;

import com.blog.community.dto.JoinDto;
import com.blog.community.dto.MemberLoginRequestDto;
import com.blog.community.dto.TokenDto;
import com.blog.community.entity.MemberEntity;
import com.blog.community.exception.MemberNotFoundException;
import com.blog.community.jwt.provider.JwtTokenProvider;
import com.blog.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    public MemberServiceImpl(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.memberRepository = memberRepository;
//    }

    public void save(JoinDto joinDto) {
        String memberEmail = joinDto.getMemberEmail();
        String memberPassword = joinDto.getMemberPassword();
        MemberEntity memberEntity = MemberEntity.createMember(memberEmail, memberPassword, "nickname",bCryptPasswordEncoder);
        memberRepository.save(memberEntity);
    }


}
