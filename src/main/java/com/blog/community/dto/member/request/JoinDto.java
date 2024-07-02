package com.blog.community.dto.member.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class JoinDto {
    private String memberEmail;
    private String memberPassword;
    private String memberNickname;
    private MultipartFile memberProfileImage;
}
