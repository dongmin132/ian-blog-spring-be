package com.blog.community.service;

import com.blog.community.dto.member.request.JoinDto;
import com.blog.community.dto.member.request.MemberUpdateRequest;
import com.blog.community.dto.member.response.MemberResponse;
import com.blog.community.entity.MemberEntity;
import com.blog.community.exception.member.CustomMemberException;
import com.blog.community.exception.member.MemberException;
import com.blog.community.repository.MemberRepository;
import com.blog.community.repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final FileService fileService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${memberImgLocation}")
    private String memberImageLocation;

    @Override
    @Transactional
    public void save(JoinDto joinDto) {
        String memberEmail = joinDto.getMemberEmail();
        String memberPassword = joinDto.getMemberPassword();
        String memberNickname = joinDto.getMemberNickname();

        if (joinDto.getMemberProfileImage() == null || joinDto.getMemberProfileImage().isEmpty()) {
            throw new CustomMemberException(MemberException.INVALID_FILE);
        }

        String originalImageName = joinDto.getMemberProfileImage().getOriginalFilename();

        try {
            String imgName = fileService.uploadFile(memberImageLocation, originalImageName, joinDto.getMemberProfileImage().getBytes());
            String imgUrl = "/images/member/" + imgName;
            MemberEntity memberEntity = MemberEntity.createMember(memberEmail, memberPassword, memberNickname, bCryptPasswordEncoder, imgUrl);
            memberRepository.save(memberEntity);
        } catch (IOException e) {
            log.error("파일 업로드 중 문제 발생", e);
            throw new CustomMemberException(MemberException.FILE_UPLOAD_FAILED);
        }
    }

    @Override
    public void isEmailExists(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new CustomMemberException(MemberException.DUPLICATE_EMAIL);
        }
    }

    @Override
    public void isNicknameExists(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new CustomMemberException(MemberException.DUPLICATE_NICKNAME);
        }
    }

    @Override
    public MemberResponse getMember(Long memberId) {
        MemberEntity memberEntity = memberRepository.findByMemberId(memberId).orElseThrow(() -> new CustomMemberException(MemberException.NOT_FOUND_MEMBER));
        return MemberResponse.from(memberEntity);

    }

    @Override
    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequest memberUpdateRequest) {
        MultipartFile memberImageFile = memberUpdateRequest.getMemberProfileImage();
        String memberNickname = memberUpdateRequest.getMemberNickname();
        String imgUrl = null;
        MemberEntity memberEntity = memberRepository.findByMemberId(memberId).orElseThrow(() -> new CustomMemberException(MemberException.NOT_FOUND_MEMBER));

        try {
            if (memberImageFile != null && !memberImageFile.isEmpty()) {
                if (memberEntity.getMemberProfileImage() != null) {
                    // 기존의 이미지를 삭제하기 위해서 파일 이름을 가져와야 하는데 디비에 저장된 경로는 클라이언트에서
                    // 서버 측에 이미지에 접근할 수 있는 부분이므로 로컬에 저장된 이미지 이름을 추출해서 경로를 맞춰줘야 한다.
                    String filePath = extractFileName(memberEntity.getMemberProfileImage());
                    fileService.deleteFile(filePath);
                }
                String imgName = fileService.uploadFile(memberImageLocation, memberImageFile.getOriginalFilename(), memberImageFile.getBytes());
                imgUrl = "/images/member/" + imgName;
            }
        } catch (IOException e) {
            log.error("파일 업로드 중 문제 발생", e);
            throw new CustomMemberException(MemberException.FILE_UPLOAD_FAILED);
        }
        memberRepository.updateMemberProfileImage(memberId, memberNickname, imgUrl);
    }

    @Override
    @Transactional
    public void updatePassword(Long memberId, String password) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        memberRepository.updatePassword(memberId, encodedPassword);
    }

    @Override
    public void deleteMember(Long memberId) {
        MemberEntity memberEntity = memberRepository.findByMemberId(memberId).orElseThrow(()->new CustomMemberException(MemberException.NOT_FOUND_MEMBER));
        if(memberEntity.getMemberProfileImage() != null) {
            String filePath = extractFileName(memberEntity.getMemberProfileImage());
            fileService.deleteFile(filePath);
        }
        memberRepository.deleteMember(memberId);
    }


    private String extractFileName(String fullPath) {
        String fileName = fullPath.substring(fullPath.lastIndexOf("/") + 1);
        return memberImageLocation + "/" + fileName;
    }
}
