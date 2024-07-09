package com.blog.community.controller;

import com.blog.community.dto.member.request.CustomUserDetails;
import com.blog.community.dto.member.request.JoinDto;
import com.blog.community.dto.member.request.MemberUpdateRequest;
import com.blog.community.dto.member.request.PasswordUpdateRequest;
import com.blog.community.service.MemberServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Provider;

import static com.blog.community.utils.ResponseUtils.createResponse;

@Controller
@RequestMapping("/members")
@RestController
public class MemberController {
    private final MemberServiceImpl memberService;
    public MemberController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(JoinDto joinDto) throws IOException {
//        memberService.save(joinDto);
        return createResponse(HttpStatus.CREATED,"회원가입 성공");
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> isEmailExists(@RequestParam String email) {
        memberService.isEmailExists(email);
        return createResponse(HttpStatus.OK, "이메일 사용 가능합니다.");
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<?> isNicknameExists(@RequestParam String nickname) {
        memberService.isNicknameExists(nickname);
        return createResponse(HttpStatus.OK, "닉네임 사용 가능합니다.");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMember(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        return createResponse(HttpStatus.OK,"회원 정보 추가" ,memberService.getMember(memberId));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> profileUpdate(@ModelAttribute  MemberUpdateRequest memberUpdateRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        memberService.isNicknameExists(memberUpdateRequest.getMemberNickname());
        memberService.updateMember(memberId, memberUpdateRequest);
        return createResponse(HttpStatus.OK,"회원 정보 수정");
    }

    @PatchMapping("/update-password")
    public ResponseEntity<?> passwordUpdate(@RequestBody PasswordUpdateRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        memberService.updatePassword(memberId, request.getPassword());
        return createResponse(HttpStatus.OK,"비밀번호 수정");
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        memberService.deleteMember(memberId);
        return createResponse(HttpStatus.OK,"회원 탈퇴");
    }


    @PostMapping("/test")
    public String test(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();

        return "redirect:/boards";
    }
}

