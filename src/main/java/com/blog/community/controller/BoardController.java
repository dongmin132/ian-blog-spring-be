package com.blog.community.controller;

import com.blog.community.dto.board.request.BoardRequest;
import com.blog.community.dto.board.request.BoardUpdateRequest;
import com.blog.community.dto.member.request.CustomUserDetails;
import com.blog.community.service.BoardService;
import com.blog.community.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor

public class BoardController {
    private final BoardService boardService;
    @GetMapping
    public ResponseEntity<?> getBoards(@RequestParam("page") int page,
                                       @RequestParam("size") int size,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = (userDetails != null) ? userDetails.getMemberId() : null;
        return ResponseUtils.createResponse(HttpStatus.OK, "게시글 전체 조회 성공", boardService.getBoards(page,size,memberId));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable("boardId") Long boardId,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = (userDetails != null) ? userDetails.getMemberId() : null;
        return ResponseUtils.createResponse(HttpStatus.OK, "게시글 조회 성공", boardService.getBoardWithComments(boardId,memberId));
    }

    @PostMapping
    public ResponseEntity<?> createBoard(BoardRequest boardRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();

        boardService.createBoard(boardRequest, memberId);
        return ResponseUtils.createResponse(HttpStatus.CREATED, "게시글 생성 성공", null);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<?> updateBoard(@RequestBody BoardUpdateRequest request, @PathVariable Long boardId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        String content = request.getContent();
        System.out.println(content);
        boardService.updateBoard(content, boardId,memberId);
        return ResponseUtils.createResponse(HttpStatus.OK, "게시글 수정 성공", null);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long memberId = userDetails.getMemberId();
        boardService.deleteBoard(boardId, memberId);
        return ResponseUtils.createResponse(HttpStatus.OK, "게시글 삭제 성공", null);
    }
}
