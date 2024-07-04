package com.blog.community.repository;

import com.blog.community.entity.RefreshTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class RefreshTokenRepository {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // RefreshToken 객체를 저장하고 TTL을 설정하는 메서드
    public void save(RefreshTokenEntity refreshToken, long duration, TimeUnit unit) {
        redisTemplate.opsForValue().set(refreshToken.getKey(), refreshToken, duration, unit);
    }

    // 토큰 문자열로 RefreshToken 객체를 조회하는 메서드
    public Optional<RefreshTokenEntity> findById(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value instanceof RefreshTokenEntity) {
            return Optional.of((RefreshTokenEntity) value);
        } else {
            return Optional.empty();
        }
    }

    // 토큰 문자열로 RefreshToken 객체를 삭제하는 메서드
    public void deleteByToken(String key) {
        redisTemplate.delete(key);
    }
}