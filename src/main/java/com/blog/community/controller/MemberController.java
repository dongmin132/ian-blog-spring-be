package com.blog.community.controller;

import com.blog.community.entity.MemberEntity;
import com.blog.community.exception.MemberNotFoundException;
import com.blog.community.service.MemberService;
import com.blog.community.service.MemberServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {
    private final MemberServiceImpl memberService;

    public MemberController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home() {
        try {
        memberService.findById(13L);
        } catch (MemberNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return "index";
    }
}
