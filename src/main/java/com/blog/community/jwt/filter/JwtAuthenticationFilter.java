package com.blog.community.jwt.filter;

import com.blog.community.exception.jwt.CustomJwtException;
import com.blog.community.jwt.provider.JwtTokenProvider;
import com.blog.community.utils.ResponseUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

// 클라이언트 요청 시 JWT 인증을 하기위해 설치하는 커스텀 필터로, UsernamePasswordAuthenticationFilter 이전에 실행됨(로그인 인증 전에 실행).
// 즉, username + password를 통한 인증을 JWT로 수행함

public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            // Request Header에서 JWT 토큰 추출
            String token = resolveToken((HttpServletRequest) request);

            // validateToken() 메서드로 유효성 검사
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (CustomJwtException e) {
            // 예외 발생 시 응답 설정
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(e.getStatus().value()); // 401 상태 코드 설정
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            String jsonResponse = String.format("{\"status\": %d, \"message\": \"%s\"}", e.getStatus().value(), e.getMessage());
            httpResponse.getWriter().write(jsonResponse);
        }
    }

    // 쿠키에서 access 토큰 추출
    private String resolveToken(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);    // "Bearer " 이후의 토큰만 추출
//        }
        if (request.getCookies() == null)
            return null;
        for (Cookie cookie : request.getCookies()) {
            if ("JWT".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
