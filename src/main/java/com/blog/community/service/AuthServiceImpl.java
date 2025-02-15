package com.blog.community.service;

import com.blog.community.dto.token.TokenDto;
import com.blog.community.entity.RefreshTokenEntity;
import com.blog.community.jwt.provider.JwtTokenProvider;
import com.blog.community.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final long REFRESH_TOKEN_DURATION = 7; // 7 days

    @Override
    @Transactional
    public TokenDto login(String email, String password) {
        // login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false 로 설정됨
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 검증 과정
        // authenticate 메서드가 실행될 때 CustomUserDetailsService의 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증된 객체를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.createAccessToken(authentication);

        //리프레시 토큰 저장
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .key(authentication.getName())
                .value(tokenDto.getAccessToken())
                .build();

        refreshTokenRepository.save(refreshToken, REFRESH_TOKEN_DURATION, TimeUnit.DAYS);
        return tokenDto;
    }

    @Override
    @Transactional
    public TokenDto reissue(String expiredAccessToken) {
        // 1. 만료된 액세스 토큰에서 사용자 정보 추출
        Authentication authentication = jwtTokenProvider.getAuthentication(expiredAccessToken);
        String username = authentication.getName();
        System.out.println("username = " + username);
        // 2. 저장소에서 Member 정보를 기반으로 Refresh Token 값 가져옴
        RefreshTokenEntity refreshToken = refreshTokenRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));



        // 3. 새로운 access 토큰 생성
        TokenDto tokenDto = null;
        tokenDto = jwtTokenProvider.createAccessToken(authentication);

        // 토큰 발급
        return tokenDto;
    }
}
