package com.blog.community.controller;

import com.blog.community.dto.member.request.JoinDto;
import com.blog.community.service.MemberServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/check-email")
    public ResponseEntity<?> isEmailExists(@RequestParam String email) {
        memberService.isEmailExists(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<?> isNicknameExists(@RequestParam String nickname) {
        memberService.isNicknameExists(nickname);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/test")
    public String test() {
        return "sucess";
    }
}
