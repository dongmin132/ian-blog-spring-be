package com.blog.community.repository.board;

import com.blog.community.dto.board.response.BoardWithMemberResponse;
import com.blog.community.dto.board.response.BoardsResponse;
import com.blog.community.dto.board.response.QBoardWithMemberResponse;
import com.blog.community.dto.board.response.QBoardsResponse;
import com.blog.community.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Slice<BoardsResponse> findBoardsWithComments(Pageable pageable, Long memberId) {
        QBoardEntity board = QBoardEntity.boardEntity;
        QMemberEntity member = QMemberEntity.memberEntity;
        QBoardImgEntity boardImage = QBoardImgEntity.boardImgEntity;
        QCommentEntity comment = QCommentEntity.commentEntity;
        QBoardLikeEntity boardLike = QBoardLikeEntity.boardLikeEntity;

        BooleanBuilder likeCondition = new BooleanBuilder();
        if (memberId != null) {
            likeCondition.and(boardLike.member.memberId.eq(memberId));
        }

        StringTemplate groupConcat = Expressions.stringTemplate(
                "group_concat({0})", boardImage.boardImageUrl
        );

        List<BoardsResponse> results = queryFactory
                .select(new QBoardsResponse(
                        board.boardId,
                        board.boardTitle,
                        board.boardContent,
                        board.boardViewCount,
                        JPAExpressions.select(comment.count()).from(comment).where(comment.board.eq(board)),
                        board.createdAt,
                        member.memberProfileImage,
                        member.memberNickname,
                        JPAExpressions.select(groupConcat).from(boardImage).where(boardImage.board.eq(board)),
                        JPAExpressions.select(boardLike.count()).from(boardLike).where(boardLike.board.eq(board).and(likeCondition)),
                        JPAExpressions.select(boardLike.count()).from(boardLike).where(boardLike.board.eq(board))
                ))
                .from(board)
                .join(board.member, member)
                .leftJoin(boardImage).on(board.boardId.eq(boardImage.board.boardId))
                .groupBy(board.boardId)
                .orderBy(board.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)  // 추가로 하나 더 가져와서 hasNext 체크
                .fetch();

        boolean hasNext = results.size() > pageable.getPageSize();
        if (hasNext) {
            results.remove(results.size() - 1);
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }

    @Override
    public BoardWithMemberResponse findBoardWithMember(Long boardId, Long memberId) {
        QMemberEntity member = QMemberEntity.memberEntity;
        QBoardEntity board = QBoardEntity.boardEntity;
        QCommentEntity comment = QCommentEntity.commentEntity;
        QBoardImgEntity boardImage = QBoardImgEntity.boardImgEntity;
        QBoardLikeEntity boardLike = QBoardLikeEntity.boardLikeEntity;

        BooleanBuilder likeCondition = new BooleanBuilder();
        if (memberId != null) {
            likeCondition.and(boardLike.member.memberId.eq(memberId));
        }

        StringTemplate groupConcat = Expressions.stringTemplate(
                "group_concat({0})", boardImage.boardImageUrl
        );

        return queryFactory
                .select(new QBoardWithMemberResponse(
                        board.boardId,
                        board.boardTitle,
                        board.boardContent,
                        board.boardViewCount,
                        JPAExpressions.select(comment.count()).from(comment).where(comment.board.eq(board)),
                        member.memberProfileImage,
                        board.createdAt,
                        member.memberNickname,
                        JPAExpressions.select(groupConcat).from(boardImage).where(boardImage.board.eq(board)),
                        JPAExpressions.select(boardLike.count()).from(boardLike).where(boardLike.board.eq(board).and(likeCondition)),
                        JPAExpressions.select(boardLike.count()).from(boardLike).where(boardLike.board.eq(board)),
                        member.memberId
                ))
                .from(board)
                .join(board.member, member)
                .leftJoin(boardImage).on(board.boardId.eq(boardImage.board.boardId))
                .where(board.boardId.eq(boardId))
                .fetchFirst();
    }
}
