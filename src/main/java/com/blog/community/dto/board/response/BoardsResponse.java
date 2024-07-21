package com.blog.community.dto.board.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardsResponse {
    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private int boardViewCount;
    private Long boardCommentCount;
    private LocalDateTime createdAt;
    private String memberProfileImage;
    private String memberNickname;
    private String boardImages;
    private Long boardLikeCount;
    private Long boardTotalLikeCount;

    @QueryProjection
    public BoardsResponse(Long boardId, String boardTitle, String boardContent, int boardViewCount, Long boardCommentCount, LocalDateTime createdAt, String memberProfileImage, String memberNickname, String boardImages, Long boardLikeCount, Long boardTotalLikeCount) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardViewCount = boardViewCount;
        this.boardCommentCount = boardCommentCount;
        this.createdAt = createdAt;
        this.memberProfileImage = memberProfileImage;
        this.memberNickname = memberNickname;
        this.boardImages = boardImages;
        this.boardLikeCount = boardLikeCount;
        this.boardTotalLikeCount = boardTotalLikeCount;
    }
}
