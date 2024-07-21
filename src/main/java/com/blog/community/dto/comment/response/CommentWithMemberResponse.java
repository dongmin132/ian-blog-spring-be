package com.blog.community.dto.comment.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentWithMemberResponse {
    private final Long commentId;
    private final String commentContent;
    private final LocalDateTime createdAt;
    private final String memberProfileImage;
    private final String memberNickname;
    private final Long memberId;

    @QueryProjection

    public CommentWithMemberResponse(Long commentId, String commentContent, LocalDateTime createdAt, String memberProfileImage, String memberNickname, Long memberId) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.createdAt = createdAt;
        this.memberProfileImage = memberProfileImage;
        this.memberNickname = memberNickname;
        this.memberId = memberId;
    }
}
