package com.blog.community.controller;

import com.blog.community.dto.comment.response.CommentWithMemberResponse;
import com.blog.community.service.CommentService;
import com.blog.community.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getComments(@PathVariable Long boardId) {

        return ResponseUtils.createResponse(HttpStatus.OK,"댓글 조회 성공",commentService.getComments(boardId));
    }


}
