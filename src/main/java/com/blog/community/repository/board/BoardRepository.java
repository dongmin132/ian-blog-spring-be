package com.blog.community.repository.board;

import com.blog.community.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardRepository extends JpaRepository<BoardEntity,Long>, QuerydslPredicateExecutor<BoardEntity>, BoardRepositoryCustom {
}
