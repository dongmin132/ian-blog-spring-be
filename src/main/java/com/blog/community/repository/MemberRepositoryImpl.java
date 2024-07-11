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
    public Optional<MemberEntity> findByMemberId(Long memberId) {
        String sql = "select * from member where member_id = ?";

        try {
            MemberEntity memberEntity = template.queryForObject(sql,memberEntityRowMapper(),memberId);
            return Optional.of(memberEntity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MemberEntity> findByEmail(String email) {
        String sql = "select * from member where member_email = ?";
        try {
            MemberEntity memberEntity = template.queryForObject(sql,memberEntityRowMapper(),email);
            return Optional.of(memberEntity);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(MemberEntity memberEntity) {
        String sql = "insert into member(member_email, member_password, member_nickname, member_profile_image) values(?,?,?,?)";
        template.update(sql, memberEntity.getMemberEmail(), memberEntity.getMemberPassword(), memberEntity.getMemberNickname(), memberEntity.getMemberProfileImage());
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "select count(*) from member where member_email = ?";
        return template.queryForObject(sql, Integer.class, email) > 0;
    }

    @Override
    public boolean existsByNickname(String nickname) {
        String sql = "select count(*) from member where member_nickname = ?";
        return template.queryForObject(sql, Integer.class, nickname) > 0;
    }

    @Override
    public void updateMemberProfileImage(Long memberId, String nickname, String profileImage) {
        if (profileImage == null) {
            String sql = "update member set member_nickname = ? where member_id = ?";
            template.update(sql, nickname, memberId);
        } else {
            String sql = "update member set member_nickname = ?, member_profile_image = ? where member_id = ?";
            template.update(sql, nickname, profileImage, memberId);
        }
    }

    @Override
    public void updatePassword(Long memberId, String encodedPassword) {
        String sql = "update member set member_password = ? where member_id = ?";
        template.update(sql,encodedPassword,memberId);
    }

    @Override
    public void deleteMember(Long memberId) {
        String sql = "delete from member where member_id = ?";
        template.update(sql, memberId);
    }


    private RowMapper<MemberEntity> memberEntityRowMapper() {
        return (rs, rowNum) -> new MemberEntity(
                    rs.getLong("member_id"),
                    rs.getString("member_email"),
                    rs.getString("member_password"),
                    rs.getString("member_nickname"),
                    rs.getString("member_profile_image")
            );
    }
}
