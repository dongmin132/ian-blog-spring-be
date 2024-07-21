package com.blog.community.repository.comment;

import com.blog.community.dto.comment.response.CommentWithMemberResponse;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentWithMemberResponse> findCommentsWithMember(Long boardId);
}
