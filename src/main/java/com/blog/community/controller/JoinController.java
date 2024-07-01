package com.blog.community.controller;

import com.blog.community.dto.JoinDto;
import com.blog.community.service.MemberService;
import com.blog.community.service.MemberServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController {
    private final MemberServiceImpl memberService;
    public JoinController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }
    @PostMapping("/register")
    public String register(JoinDto joinDto) {
        memberService.save(joinDto);
        return "200 OK";
    }


}
