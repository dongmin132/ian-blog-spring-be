package com.blog.community.service;

import com.blog.community.dto.board.request.BoardRequest;
import com.blog.community.dto.board.response.BoardWithMemberResponse;
import com.blog.community.dto.board.response.BoardsResponse;
import org.springframework.data.domain.Slice;

public interface BoardService {
    Slice<BoardsResponse> getBoards(int page, int size, Long memberId);

    BoardWithMemberResponse getBoardWithComments(Long boardId, Long memberId);

    void createBoard(BoardRequest boardRequest, Long memberId);

    void updateBoard(String content,Long boardId, Long memberId);

    void deleteBoard(Long boardId, Long memberId);
}
