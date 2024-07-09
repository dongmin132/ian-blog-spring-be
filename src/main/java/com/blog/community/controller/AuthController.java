package com.blog.community.controller;

import com.blog.community.dto.member.request.MemberLoginRequestDto;
import com.blog.community.dto.token.TokenDto;
import com.blog.community.service.AuthService;
import com.blog.community.utils.GetCookieValue;
import com.blog.community.utils.ResponseUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto, HttpServletResponse response) throws IOException {
        String email = memberLoginRequestDto.getEmail();
        String password = memberLoginRequestDto.getPassword();
        try {
            TokenDto tokenDto = authService.login(email, password);
            Cookie accessTokenCookie = new Cookie("JWT", tokenDto.getAccessToken());
            accessTokenCookie.setHttpOnly(true);
//            accessTokenCookie.setSecure(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(60 * 15 * 10000); // 15분 동안 유효, 쿠키의 만료 시간이 액세스 토큰 만료시간보다 길어야 만료되었을 때 리프레시 토큰으로 구현이 가능
            response.addCookie(accessTokenCookie);

//            response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
            return ResponseUtils.createResponse(HttpStatus.OK, "로그인에 성공했습니다.");
        } catch(UsernameNotFoundException | BadCredentialsException e) {
            return ResponseUtils.createResponse(HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseUtils.createResponse(HttpStatus.OK, "로그아웃에 성공했습니다.");
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        try {
            String accessToken = GetCookieValue.getCookieValue(request, "JWT");
            TokenDto newTokenDto = authService.reissue(accessToken);
            Cookie accessTokenCookie = new Cookie("JWT", newTokenDto.getAccessToken());
            accessTokenCookie.setHttpOnly(true);
//            accessTokenCookie.setSecure(true);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge(60 * 15); // 15분 동안 유효
            response.addCookie(accessTokenCookie);

            return ResponseUtils.createResponse(HttpStatus.OK, "토큰 재발급에 성공했습니다.");
//        } catch (Exception e) {
//            return ResponseUtils.createResponse(HttpStatus.UNAUTHORIZED, "토큰 재발급에 실패했습니다: " + e.getMessage());
//        }
    }
}
