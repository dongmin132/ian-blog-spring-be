package com.blog.community.repository;

import com.blog.community.entity.MemberEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository{
    JdbcTemplate template;

    public MemberRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<MemberEntity> findById(Long memberId) {
        String sql = "select * from member where member_id = ?";

        try {
            MemberEntity memberEntity = template.queryForObject(sql,memberEntityRowMapper(),memberId);
            return Optional.of(memberEntity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<MemberEntity> memberEntityRowMapper() {
        return (rs, rowNum) -> new MemberEntity(
                    rs.getLong("member_id"),
                    rs.getString("member_email"),
                    rs.getString("member_password"),
                    rs.getString("member_nickname"),
                    rs.getString("member_profileImage")
            );
    }
}
