package com.blog.community.service;

import com.blog.community.dto.member.request.JoinDto;
import com.blog.community.entity.MemberEntity;
import com.blog.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl {
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    public void save(JoinDto joinDto) throws IOException {
        String memberEmail = joinDto.getMemberEmail();
        String memberPassword = joinDto.getMemberPassword();
        String memberNickname = joinDto.getMemberNickname();
        String originalImageName = joinDto.getMemberProfileImage().getOriginalFilename();

        if(originalImageName != null && !originalImageName.isEmpty()) {
            String imgName = fileService.uploadFile(itemImgLocation,originalImageName,joinDto.getMemberProfileImage().getBytes());
            String imgUrl = "/images/member/" + imgName;
            MemberEntity memberEntity = MemberEntity.createMember(memberEmail, memberPassword, memberNickname,bCryptPasswordEncoder,imgUrl);
            memberRepository.save(memberEntity);
        }

    }


}
