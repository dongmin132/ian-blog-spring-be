package com.blog.community.controller;

import com.blog.community.dto.member.request.JoinDto;
import com.blog.community.service.MemberServiceImpl;
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
    public String register(JoinDto joinDto) throws IOException {
        memberService.save(joinDto);
        return "200 OK";
    }

    @PostMapping("/test")
    public String test() {
        return "sucess";
    }
}
