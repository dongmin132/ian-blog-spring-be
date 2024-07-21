package com.blog.community.dto.board.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardRequest {
    private String boardTitle;
    private String boardContent;
    private List<MultipartFile> boardImages;
}
