package com.blog.community.controller;

import com.blog.community.dto.member.request.CustomUserDetails;
import com.blog.community.service.BoardService;
import com.blog.community.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor

public class BoardController {
    private final BoardService boardService;
    @GetMapping
    public ResponseEntity<?> getBoards(@RequestParam("page") int page,
                                       @RequestParam("size") int size) {
        Long memberId = 1L;
        return ResponseUtils.createResponse(HttpStatus.OK, "게시글 조회 성공", boardService.getBoardsWithComments(page,size,memberId));
    }
}
