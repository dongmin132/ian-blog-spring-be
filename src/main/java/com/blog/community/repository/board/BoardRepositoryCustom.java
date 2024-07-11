package com.blog.community.repository.board;

import com.blog.community.dto.board.response.BoardsWithCommentsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BoardRepositoryCustom {
    Slice<BoardsWithCommentsResponse> findBoardsWithComments(Pageable pageable, Long memberId);
}
