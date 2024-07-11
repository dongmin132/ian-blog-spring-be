package com.blog.community.service;

import com.blog.community.dto.board.response.BoardsWithCommentsResponse;
import com.blog.community.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public Slice<BoardsWithCommentsResponse> getBoardsWithComments(int page, int size, Long memberId) {
        Pageable pageable = PageRequest.of(page, size);
        return boardRepository.findBoardsWithComments(pageable, memberId);
    }
}
