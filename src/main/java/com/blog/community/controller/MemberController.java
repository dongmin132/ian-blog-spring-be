package com.blog.community.controller;

import com.blog.community.dto.member.request.CustomUserDetails;
import com.blog.community.dto.member.request.JoinDto;
import com.blog.community.service.MemberServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
        memberService.save(joinDto);
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



    @PostMapping("/test")
    public String test(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        System.out.println(memberId);
        return "redirect:/boards";
    }
}
