package com.blog.community.controller;

import com.blog.community.dto.MemberLoginRequestDto;
import com.blog.community.dto.TokenDto;
import com.blog.community.entity.MemberEntity;
import com.blog.community.exception.MemberNotFoundException;
import com.blog.community.service.MemberService;
import com.blog.community.service.MemberServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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


    @PostMapping("/test")
    public String test() {
        return "sucess";
    }
}
