package com.blog.community.service;

import com.blog.community.dto.token.TokenDto;


public interface AuthService {
    TokenDto login(String email, String password);

    TokenDto reissue(String refreshToken);
}
