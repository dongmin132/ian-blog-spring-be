package com.blog.community.repository.board;

import com.blog.community.dto.board.response.BoardWithMemberResponse;
import com.blog.community.dto.board.response.BoardsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BoardRepositoryCustom {
    Slice<BoardsResponse> findBoardsWithComments(Pageable pageable, Long memberId);
    BoardWithMemberResponse findBoardWithMember(Long boardId, Long memberId);
}
