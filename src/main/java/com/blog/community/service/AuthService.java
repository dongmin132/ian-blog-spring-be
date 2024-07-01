package com.blog.community.service;

import com.blog.community.dto.TokenDto;
import org.springframework.stereotype.Service;


public interface AuthService {
    public TokenDto login(String email, String password);

    public TokenDto reissue(String refreshToken);
}
