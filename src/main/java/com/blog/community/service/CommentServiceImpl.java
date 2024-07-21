package com.blog.community.service;

import com.blog.community.dto.comment.response.CommentWithMemberResponse;
import com.blog.community.exception.comment.CommentException;
import com.blog.community.exception.comment.CustomCommentException;
import com.blog.community.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CommentWithMemberResponse> getComments(Long boardId) {
        try {
            return commentRepository.findCommentsWithMember(boardId);
        } catch(NullPointerException | IllegalArgumentException e) {
            throw new CustomCommentException(CommentException.NOT_FOUND_BOARD, e.getMessage());
        }

    }
}
