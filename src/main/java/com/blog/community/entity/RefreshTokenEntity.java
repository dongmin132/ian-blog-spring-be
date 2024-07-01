package com.blog.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Table(name = "refresh_token")
@Getter
@Entity
public class RefreshTokenEntity {
    @Id
    @Column(name = "rt_key")
    private String key;

    @Column(name= "rt_value")
    private String value;

    public RefreshTokenEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RefreshTokenEntity updateValue(String token) {
        this.value=token;
        return this;
    }



}

