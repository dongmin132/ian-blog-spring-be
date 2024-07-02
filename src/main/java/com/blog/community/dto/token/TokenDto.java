package com.blog.community.dto.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

//클라이언트에 토큰을 보내는 RequestDTO
@Builder
@AllArgsConstructor
@Getter
@ToString
public class TokenDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;


}
