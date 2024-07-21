package com.blog.community.service;

import com.blog.community.dto.board.request.BoardRequest;
import com.blog.community.dto.board.response.BoardWithMemberResponse;
import com.blog.community.dto.board.response.BoardsResponse;
import com.blog.community.entity.BoardEntity;
import com.blog.community.entity.BoardImgEntity;
import com.blog.community.entity.MemberEntity;
import com.blog.community.exception.board.BoardException;
import com.blog.community.exception.board.CustomBoardException;
import com.blog.community.exception.member.CustomMemberException;
import com.blog.community.repository.MemberRepository;
import com.blog.community.repository.board.BoardImgRepository;
import com.blog.community.repository.board.BoardRepository;
import com.querydsl.core.NonUniqueResultException;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.blog.community.exception.board.BoardException.*;
import static com.blog.community.exception.member.MemberException.NOT_FOUND_MEMBER;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final FileService fileService;
    private final BoardImgRepository boardImgRepository;

    @Value("${boardImgLocation}")
    private String boardImageLocation;

    @Override
    @Transactional(readOnly = true)
    public Slice<BoardsResponse> getBoards(int page, int size, Long memberId) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            return boardRepository.findBoardsWithComments(pageable, memberId);
        } catch (NonUniqueResultException e) {
            throw new CustomBoardException(NON_UNIQUE_RESULT, e.getMessage());
        } catch (NoResultException e) {
            throw new CustomBoardException(NOT_FOUND_BOARD);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public BoardWithMemberResponse getBoardWithComments(Long boardId, Long memberId) {
        try {
            return boardRepository.findBoardWithMember(boardId, memberId);
        } catch (NonUniqueResultException e) {
            throw new CustomBoardException(NON_UNIQUE_RESULT, e.getMessage());
        } catch (NoResultException e) {
            throw new CustomBoardException(NOT_FOUND_BOARD);
        }
    }


    @Override
    @Transactional
    public void createBoard(BoardRequest boardRequest, Long memberId) {
        String title = boardRequest.getBoardTitle();
        String content = boardRequest.getBoardContent();


        if (title == null || title.isEmpty() || content == null || content.isEmpty()) {
            throw new CustomBoardException(BoardException.INVALID_INPUT);
        }
        MemberEntity member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new CustomMemberException(NOT_FOUND_MEMBER));

        BoardEntity boardEntity = BoardEntity.createBoard(title, content, member);
        if (boardRequest.getBoardImages() != null && !boardRequest.getBoardImages().isEmpty()) {
            int index = 0;
            for (MultipartFile boardImage : boardRequest.getBoardImages()) {
                String originalBoardImageName = boardImage.getOriginalFilename();
                try {
                    String imgName = fileService.uploadFile(boardImageLocation, originalBoardImageName, boardImage.getBytes());
                    String imgUrl = "/img/boards/" + imgName;
                    String repImgYn = index == 0 ? "Y" : "N";
                    BoardImgEntity boardImageEntity = BoardImgEntity.createBoardImg(imgName, imgUrl, repImgYn, boardEntity);

                    boardEntity.addBoardImage(boardImageEntity);
                } catch (IOException e) {
                    throw new CustomBoardException(FILE_UPLOAD_FAILED, e.getMessage());
                }
                index++;
            }
        }
        boardRepository.save(boardEntity);
    }

    @Override
    @Transactional
    public void updateBoard(String content, Long boardId, Long memberId) {
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomBoardException(NOT_FOUND_BOARD));
        if (!board.getMember().getMemberId().equals(memberId)) {
            throw new CustomBoardException(NOT_MATCH_MEMBER);
        }
        board.updateContent(content);

        boardRepository.save(board);
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId, Long memberId) {
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomBoardException(NOT_FOUND_BOARD));
        if (!board.getMember().getMemberId().equals(memberId)) {
            throw new CustomBoardException(NOT_MATCH_MEMBER);
        }
        for (BoardImgEntity boardImg : board.getBoardImages()) {
            String filePath = boardImageLocation + "/"+boardImg.getBoardImageName();
            fileService.deleteFile(filePath);
        }

        boardRepository.delete(board);
    }
}
