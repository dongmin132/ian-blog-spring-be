package com.blog.community.repository.comment;

import com.blog.community.entity.BoardEntity;
import com.blog.community.entity.CommentEntity;
import com.blog.community.repository.board.BoardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommentRepository extends JpaRepository<CommentEntity, Long>, QuerydslPredicateExecutor<CommentEntity>, CommentRepositoryCustom {
}
