package com.blog.community.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardImageDto {
    private final Long boardId;
    private final String boardTitle;
    private final String boardContent;
    private final int boardViewCount;
    private final int boardCommentCount;
    private final LocalDateTime createdAt;
    private final String memberProfileImage;
    private final String memberNickname;
    private final List<String> boardImages;
    private final int boardLikeCount;
    private final int boardTotalLikeCount;

    @QueryProjection
    public BoardImageDto(Long boardId, String boardTitle, String boardContent, int boardViewCount, int boardCommentCount, LocalDateTime createdAt, String memberProfileImage, String memberNickname, List<String> boardImages, int boardLikeCount, int boardTotalLikeCount) {
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
