package com.blog.community.service;

import com.blog.community.dto.comment.response.CommentWithMemberResponse;

import java.util.List;

public interface CommentService {
    List<CommentWithMemberResponse> getComments(Long boardId);
}
