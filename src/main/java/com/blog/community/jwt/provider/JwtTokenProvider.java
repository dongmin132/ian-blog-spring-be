package com.blog.community.jwt.provider;

import com.blog.community.dto.member.request.CustomUserDetails;
import com.blog.community.dto.token.TokenDto;
import com.blog.community.exception.jwt.CustomJwtException;
import com.blog.community.exception.jwt.JwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

import static com.blog.community.exception.jwt.JwtException.INVALID_TOKEN;

@Slf4j
@Component
public class JwtTokenProvider {
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 15 *60 *1000* 1000;            // 15분(테스트 용으로 좀 길게 잡음)
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    private static final long THREE_DAYS = 1000 * 60 * 60 * 24 * 3;  // 3일

    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        try {
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            this.key = Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid jwt.secret value. Make sure it is a valid Base64-encoded string.", e);
        }
    }

//    public TokenDto generateToken(Authentication authentication) {
//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        System.out.println("customUserDetails = " + authentication.getPrincipal());
//        Long memberId = customUserDetails.getMemberId(); // getMemberId 메서드를 사용하여 memberId를 가져옵니다.
//
////        //권한 가져오기
////        String authorities = authentication.getAuthorities().stream()
////                .map(GrantedAuthority::getAuthority)
////                .collect(Collectors.joining(","));
//
//        long now = (new Date()).getTime();
//        //Access Token 생성
//        //1일: 24 * 60 * 60 * 1000 = 86400000
//        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
//        String accessToken = Jwts.builder()
//                .setSubject(authentication.getName())   //인증된 사용자의 이름을 주제로 설정
//                .claim("memberId",memberId)         //JWT의 페이로드에 권한 정보를 "auth" 라는 키로 추가
//                .setExpiration(accessTokenExpiresIn)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//
//        //Refresh Token 생성
//        String refreshToken = Jwts.builder()
//                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//
//        return TokenDto.builder()
//                .grantType("Bearer")
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
//                .build();
//    }

    public TokenDto createAccessToken(Authentication authentication) {
        // 권한들 가져오기
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        Long memberId = customUserDetails.getMemberId(); // getMemberId 메서드를 사용하여 memberId를 가져옵니다.

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
                .claim("memberId", memberId)        // payload "auth": "USER"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 151621022 (ex)
                .signWith(key, SignatureAlgorithm.HS256)    // header "alg": "HS512"
                .compact();

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .build();
    }

    //JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        //토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("memberId") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

//        // 클레임에서 권한 정보 가져오기
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get("auth").toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .toList();

        //UserDetails 객체를 생성하여 Authentication 객체를 리턴
        Long memberId = Long.parseLong(claims.get("memberId").toString());
        CustomUserDetails principal = new CustomUserDetails(memberId,claims.getSubject(),"");
        return new UsernamePasswordAuthenticationToken(principal,"",Collections.emptyList());
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        // 토큰의 서명이 유효하지 않거나 토큰이 올바른 형식이 아닌 경우 예외 처리
        catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new CustomJwtException(JwtException.INVALID_TOKEN);
        } catch (UnsupportedJwtException e) {   //지원되지 않는 JWT 토큰
            throw new CustomJwtException(JwtException.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {  //JWT 토큰이 비어있는 경우
            throw new CustomJwtException(JwtException.EMPTY_TOKEN);
        } catch (ExpiredJwtException e) {   //토큰이 만료된 경우
            log.info("토큰 만료");
        }
        return false;
    }

//    public boolean refreshTokenPeriodCheck(String refreshToken) {
//        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);
//        long now = (new Date()).getTime();
//        long refreshTokenExpiresIn = claimsJws.getBody().getExpiration().getTime();
//        long refreshTokenNowTime = new Date(now + REFRESH_TOKEN_EXPIRE_TIME).getTime();
//
//        //리프레시 토큰 만료시간이 현재 시간으로부터 3일 이상 차이나면 true 반환
//        if(refreshTokenNowTime - refreshTokenExpiresIn > THREE_DAYS) {
//            return true;
//        }
//        return false;
//    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch(ExpiredJwtException e) {
            System.out.println(e.getClaims());
            return e.getClaims();
        }
    }


//    public Claims getClaimsFromExpiredToken(String expiredAccessToken) {
//        try {
//            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(expiredAccessToken).getBody();
//        } catch (ExpiredJwtException e) {
//            return e.getClaims(); // 만료된 토큰의 경우, 클레임을 반환
//        }
//    }

}
