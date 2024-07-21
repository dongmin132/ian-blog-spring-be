package com.blog.community.dto.board.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class BoardUpdateRequest {
//    private String boardTitle;
    private String content;
//    private List<MultipartFile> boardImages;
//
////    public static BoardUpdateRequest of(String boardTitle, String boardContent, List<MultipartFile> boardImages) {
////        return new BoardUpdateRequest(boardTitle, boardContent, boardImages);
////    }
////
////    public static BoardUpdateRequest of(String boardTitle, String boardContent) {
////        return new BoardUpdateRequest(boardTitle, boardContent, null);
////    }
}
