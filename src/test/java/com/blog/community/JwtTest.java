package com.blog.community;

import com.blog.community.dto.CustomUserDetails;
import com.blog.community.dto.TokenDto;
import com.blog.community.entity.MemberEntity;
import com.blog.community.jwt.provider.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtTest {
    private JwtTokenProvider jwtTokenProvider;
    private CustomUserDetails customUserDetails;
    private Authentication authentication;
    private final String secret = "VlwEyVBsYt9V7zq57TejMnVUyzblYcfPQye08f7MGVA9XkHaaaaaabbbbbbbbsdfasdkljaslkdjwasd";

    @BeforeEach
    public void setUp() {
        jwtTokenProvider = new JwtTokenProvider(secret);

        MemberEntity memberEntity = MemberEntity.createMember(
                "email",
                "password",
                "nickname",
                new BCryptPasswordEncoder()
        );

        customUserDetails = new CustomUserDetails(memberEntity);
        authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    }

    @Test
    public void testGenerateAndValidateToken() {
        // JWT 생성
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
        System.out.println("Generated Token: " + tokenDto.getAccessToken());

        // JWT 검증
        boolean isValid = jwtTokenProvider.validateToken(tokenDto.getAccessToken());
        System.out.println("Is Token Valid: " + isValid);
        assertTrue(isValid);

        // JWT에서 사용자 정보 추출
        Authentication auth = jwtTokenProvider.getAuthentication(tokenDto.getAccessToken());
        System.out.println("Authenticated User: " + auth.getName());
        assertEquals(customUserDetails.getUsername(), auth.getName());
    }
}
