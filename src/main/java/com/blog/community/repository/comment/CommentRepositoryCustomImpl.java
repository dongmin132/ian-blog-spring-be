package com.blog.community.repository.comment;

import com.blog.community.dto.comment.response.CommentWithMemberResponse;
import com.blog.community.dto.comment.response.QCommentWithMemberResponse;
import com.blog.community.entity.QBoardEntity;
import com.blog.community.entity.QCommentEntity;
import com.blog.community.entity.QMemberEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public CommentRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<CommentWithMemberResponse> findCommentsWithMember(Long boardId) {
        QMemberEntity member = QMemberEntity.memberEntity;
        QCommentEntity comment = QCommentEntity.commentEntity;


        return queryFactory
                .select(new QCommentWithMemberResponse(
                        comment.commentId,
                        comment.commentContent,
                        comment.createdAt,
                        member.memberProfileImage,
                        member.memberNickname,
                        member.memberId
                ))
                .from(comment)
                .join(comment.member,member)
                .where(comment.board.boardId.eq(boardId))
                .orderBy(comment.createdAt.desc())
                .fetch();
    }
}
