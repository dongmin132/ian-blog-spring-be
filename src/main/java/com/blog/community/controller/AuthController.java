package com.blog.community.controller;

import com.blog.community.dto.member.request.MemberLoginRequestDto;
import com.blog.community.dto.token.TokenDto;
import com.blog.community.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/login")
    public void login(@RequestBody MemberLoginRequestDto memberLoginRequestDto, HttpServletResponse response) throws IOException {
        String email = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();

        try {
            TokenDto tokenDto = authService.login(email, password);
            response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch(UsernameNotFoundException | BadCredentialsException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("login fail: "+ e.getMessage());
        }
    }

//    @PostMapping("/reissue") {}
}
