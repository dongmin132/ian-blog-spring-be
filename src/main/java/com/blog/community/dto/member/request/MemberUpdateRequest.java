package com.blog.community.dto.member.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MemberUpdateRequest {
    private String memberNickname;
    private MultipartFile memberProfileImage;

}



